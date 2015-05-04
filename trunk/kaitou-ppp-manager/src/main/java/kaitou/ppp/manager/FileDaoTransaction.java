package kaitou.ppp.manager;

import com.womai.bsp.tool.utils.CollectionUtil;
import org.aspectj.lang.JoinPoint;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import static kaitou.ppp.common.utils.FileUtil.copy;
import static kaitou.ppp.common.utils.FileUtil.delete;
import static kaitou.ppp.common.utils.JsonValidator.validateFile;
import static kaitou.ppp.domain.BaseDomain.*;

/**
 * 文件DAO事务管理.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 18:41
 */
public class FileDaoTransaction {

    /**
     * 开启事务
     *
     * @param joinPoint 切点
     */
    public void doBefore(JoinPoint joinPoint) {
        FileDaoManager daoManager = (FileDaoManager) joinPoint.getTarget();
        daoManager.openTransaction();
    }

    /**
     * 提交事务
     *
     * @param joinPoint 切点
     */
    public void doAfter(JoinPoint joinPoint) {
        FileDaoManager daoManager = (FileDaoManager) joinPoint.getTarget();
        commit(daoManager);
    }

    /**
     * 异常回滚
     *
     * @param joinPoint 切点
     * @param ex        异常
     */
    public void doThrowing(JoinPoint joinPoint, Throwable ex) {
        FileDaoManager daoManager = (FileDaoManager) joinPoint.getTarget();
        rollback(daoManager);
        throw new RuntimeException(ex);
    }

    /**
     * 提交
     * <ul>
     * <li>备份文件覆盖源文件</li>
     * <li>删除备份文件</li>
     * </ul>
     *
     * @param daoManager DB文件事务管理层
     */
    public void commit(FileDaoManager daoManager) {
        String dbDir = daoManager.getDbDir();
        File[] backFiles = getBackFile(dbDir, daoManager.getEntityName());
        if (CollectionUtil.isEmpty(backFiles)) {
            return;
        }
        for (File backFile : backFiles) {
            String backFilePath = backFile.getPath();
            if (!validateFile(backFilePath)) {
                rollback(daoManager);
                throw new RuntimeException("back文件：" + backFilePath + "损坏！已执行回滚！");
            }
        }
        List<String> toUpgradeDBList = new ArrayList<String>();
        for (File backFile : backFiles) {
            String backFileName = backFile.getName();
            String dbFileName = backFileName.substring(0, backFileName.lastIndexOf('.'));
            String backFilePath = backFile.getPath();
            copy(backFilePath, dbDir + '\\' + dbFileName);
            delete(backFilePath);
            if (dbFileName.endsWith(CONFIG_SUFFIX)) {
                continue;
            }
            toUpgradeDBList.add(dbFileName);
        }
        daoManager.getLocalDBVersionDao().upgrade(toUpgradeDBList);
        daoManager.closeTransaction();
    }

    /**
     * 获取备份文件集合
     *
     * @param dbDir      db文件目录
     * @param entityName 实体名
     * @return 备份文件集合
     */
    private File[] getBackFile(String dbDir, final String entityName) {
        File dirFile = new File(dbDir);
        return dirFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("_" + entityName + DB_SUFFIX + BACK_SUFFIX) || name.endsWith(entityName + DB_SUFFIX + BACK_SUFFIX) || name.endsWith(entityName + CONFIG_SUFFIX + BACK_SUFFIX);
            }
        });
    }

    /**
     * 回滚
     * <p>
     * 删除备份文件
     * </p>
     *
     * @param daoManager DB文件事务管理层
     */
    public void rollback(FileDaoManager daoManager) {
        File[] backFiles = getBackFile(daoManager.getDbDir(), daoManager.getEntityName());
        if (CollectionUtil.isEmpty(backFiles)) {
            return;
        }
        for (File backFile : backFiles) {
            delete(backFile.getPath());
        }
        daoManager.closeTransaction();
    }
}
