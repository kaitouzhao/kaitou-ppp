package kaitou.ppp.manager;

import com.womai.bsp.tool.utils.CollectionUtil;
import org.aspectj.lang.JoinPoint;

import java.io.File;
import java.io.FilenameFilter;

import static kaitou.ppp.common.utils.FileUtil.copy;
import static kaitou.ppp.common.utils.FileUtil.delete;

/**
 * 文件DAO事务管理.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 18:41
 */
public class FileDaoTransaction {

    /**
     * 提交事务
     *
     * @param joinPoint 切点
     */
    public void doAfter(JoinPoint joinPoint) {
        FileDaoManager daoManager = (FileDaoManager) joinPoint.getTarget();
        commit(daoManager.getDbDir(), daoManager.getEntityName());
    }

    /**
     * 异常回滚
     *
     * @param joinPoint 切点
     * @param ex        异常
     */
    public void doThrowing(JoinPoint joinPoint, Throwable ex) {
        FileDaoManager daoManager = (FileDaoManager) joinPoint.getTarget();
        rollback(daoManager.getDbDir(), daoManager.getEntityName());
        throw new RuntimeException(ex);
    }

    /**
     * 提交
     * <ul>
     * <li>备份文件覆盖源文件</li>
     * <li>删除备份文件</li>
     * </ul>
     *
     * @param dbDir      db文件目录
     * @param entityName 实体名
     */
    public void commit(String dbDir, final String entityName) {
        File[] backFiles = getBackFile(dbDir, entityName);
        if (CollectionUtil.isEmpty(backFiles)) {
            return;
        }
        for (int i = 0; i < backFiles.length; i++) {
            File backFile = backFiles[i];
            String backFileName = backFile.getName();
            String dbFileName = backFileName.substring(0, backFileName.indexOf('.')) + ".kdb";
            String backFilePath = backFile.getPath();
            copy(backFilePath, dbDir + '\\' + dbFileName);
            delete(backFilePath);
        }
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
                return name.endsWith("_" + entityName + ".kdb.back");
            }
        });
    }

    /**
     * 回滚
     * <p>
     * 删除备份文件
     * </p>
     *
     * @param dbDir      db文件目录
     * @param entityName 实体名
     */
    public void rollback(String dbDir, final String entityName) {
        File[] backFiles = getBackFile(dbDir, entityName);
        if (CollectionUtil.isEmpty(backFiles)) {
            return;
        }
        for (int i = 0; i < backFiles.length; i++) {
            delete(backFiles[i].getPath());
        }
    }
}
