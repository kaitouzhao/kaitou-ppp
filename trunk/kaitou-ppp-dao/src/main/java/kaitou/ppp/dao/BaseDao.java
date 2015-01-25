package kaitou.ppp.dao;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.womai.bsp.tool.utils.JsonUtil.json2Object;
import static com.womai.bsp.tool.utils.JsonUtil.object2Json;
import static kaitou.ppp.common.utils.FileUtil.*;

/**
 * DAO父类.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 11:53
 */
public abstract class BaseDao<T extends BaseDomain> {

    protected final Log log = LogFactory.getLog("DAO");

    /**
     * 数据文件目录
     */
    protected String dbDir;

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    public abstract Class<T> getDomainClass();

    /**
     * 设置DB目录
     *
     * @param dbDir DB目录
     */
    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    /**
     * 获取备份文件集合
     *
     * @return 备份文件集合
     */
    protected File[] getBackFile() {
        File dirFile = new File(dbDir);
        final String domainName = getDomainClass().getSimpleName();
        return dirFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("_" + domainName + ".kdb.back");
            }
        });
    }

    /**
     * 提交
     */
    protected void commit() {
        File[] backFiles = getBackFile();
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
     * 回滚
     */
    protected void rollback() {
        File[] backFiles = getBackFile();
        if (CollectionUtil.isEmpty(backFiles)) {
            return;
        }
        for (int i = 0; i < backFiles.length; i++) {
            delete(backFiles[i].getPath());
        }
    }

    /**
     * 保存/更新
     *
     * @param domains 实体集合。支持一个或多个
     * @return 成功保存记录数
     */
    public int save(Object... domains) {
        if (CollectionUtil.isEmpty(domains)) {
            return 0;
        }
        Map<String, List<T>> domainMap = new HashMap<String, List<T>>();
        int size = domains.length;
        int updateIndex = -1;
        for (int i = 0; i < size; i++) {
            T domain = (T) domains[i];
            try {
                domain.check();
            } catch (RuntimeException e) {
                log.info("第" + (i + 1) + "行数据校验不通过", e);
                continue;
            }
            String backDbFileName = domain.backDbFileName();
            List<T> domainList = domainMap.get(backDbFileName);
            if (domainList == null) {
                domainList = query(domain.dbFileSuffix());
                if (CollectionUtil.isEmpty(domainList)) {
                    domainList = new ArrayList<T>();
                }
                domainMap.put(backDbFileName, domainList);
            }
            if (CollectionUtil.isEmpty(domainList)) {
                domainList.add(domain);
                continue;
            }
            for (int j = 0; j < domainList.size(); j++) {
                if (domain.equals(domainList.get(j))) {
                    updateIndex = j;
                }
            }
            if (updateIndex > -1) {
                domainList.remove(updateIndex);
                domainList.add(updateIndex, domain);
                updateIndex = -1;
            } else {
                domainList.add(domain);
            }
        }
        int successCount = 0;
        for (Map.Entry<String, List<T>> item : domainMap.entrySet()) {
            StringBuilder dbFilePath = new StringBuilder(dbDir).append('/').append(item.getKey());
            List<T> domainList = item.getValue();
            List<String> eJsonList = new ArrayList<String>();
            for (Object domain : domainList) {
                eJsonList.add(object2Json(domain));
            }
            successCount += writeLines(dbFilePath.toString(), eJsonList);
        }
        return successCount;
    }

    /**
     * 查询
     *
     * @param dbFileSuffix DB文件后缀
     * @return 实体列表
     */
    protected List<T> query(final String dbFileSuffix) {
        File file = new File(dbDir);
        File[] dbFiles = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(dbFileSuffix);
            }
        });
        List<T> domainList = new ArrayList<T>();
        if (!CollectionUtil.isEmpty(dbFiles)) {
            List<String> lines = readLines(dbFiles[0].getPath());
            if (!CollectionUtil.isEmpty(lines)) {
                for (String line : lines) {
                    domainList.add(json2Object(line, getDomainClass()));
                }
            }
        }
        return domainList;
    }

    /**
     * 查询
     *
     * @param dbType DB文件类型。如果为空，则默认获取全部
     * @return 实体列表
     */
    public List<T> query(String... dbType) {
        List<T> engineers = new ArrayList<T>();
        final String domainName = getDomainClass().getSimpleName();
        if (!CollectionUtil.isEmpty(dbType)) {
            for (String sId : dbType) {
                String dbFileSuffix = '_' + sId + '_' + domainName + ".kdb";
                engineers.addAll(query(dbFileSuffix));
            }
            return engineers;
        }
        File dbDirFile = new File(dbDir);
        File[] dbFiles = dbDirFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("_" + domainName + ".kdb");
            }
        });
        for (File dbFile : dbFiles) {
            String dbFileName = dbFile.getName();
            String dbFileSuffix = dbFileName.substring(dbFileName.indexOf('_', 1), dbFileName.lastIndexOf('_')) + '_' + domainName + ".kdb";
            engineers.addAll(query(dbFileSuffix));
        }
        return engineers;
    }
}