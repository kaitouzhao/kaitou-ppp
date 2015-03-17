package kaitou.ppp.dao;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.domain.BaseDomain;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.womai.bsp.tool.utils.JsonUtil.json2Object;
import static com.womai.bsp.tool.utils.JsonUtil.object2Json;
import static kaitou.ppp.common.utils.FileUtil.readLines;
import static kaitou.ppp.common.utils.FileUtil.writeLines;

/**
 * DAO父类.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 11:53
 */
public abstract class BaseDao<T extends BaseDomain> extends BaseLogManager {

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
     * 保存/更新
     *
     * @param domains 实体集合。支持一个或多个
     * @return 成功保存记录数
     */
    @SuppressWarnings("unchecked")
    public int save(Object... domains) {
        if (CollectionUtil.isEmpty(domains)) {
            return 0;
        }
        preSave(domains);
        Map<String, List<T>> domainMap = new HashMap<String, List<T>>();
        int size = domains.length;
        int updateIndex = -1;
        int successCount = 0;
        int i = 0;
        while (i < size) {
            T domain = (T) domains[i];
            try {
                domain.check();
            } catch (RuntimeException e) {
                logOperation("第" + (i + 1) + "行数据校验不通过。原因：" + e.getMessage());
                i++;
                continue;
            }
            successCount++;
            String backDbFileName = domain.backDbFileName();
            List<T> domainList = domainMap.get(backDbFileName);
            if (domainList == null) {
                domainList = query(domain.dbFileName());
                if (CollectionUtil.isEmpty(domainList)) {
                    domainList = new ArrayList<T>();
                }
                domainMap.put(backDbFileName, domainList);
            }
            if (CollectionUtil.isEmpty(domainList)) {
                domainList.add(domain);
                i++;
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
            i++;
        }
        for (Map.Entry<String, List<T>> item : domainMap.entrySet()) {
            List<T> domainList = item.getValue();
            List<String> eJsonList = new ArrayList<String>();
            for (Object domain : domainList) {
                eJsonList.add(object2Json(domain));
            }
            try {
                writeLines(dbDir + '/' + item.getKey(), eJsonList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return successCount;
    }

    /**
     * 保存/更新前操作
     * <p>
     * 保存/更新前如有特殊操作，请覆盖此方法
     * </p>
     *
     * @param domains 实体集合。支持一个或多个
     */
    public void preSave(Object... domains) {

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
            for (File dbFile : dbFiles) {
                List<String> lines;
                try {
                    lines = readLines(dbFile.getPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (!CollectionUtil.isEmpty(lines)) {
                    for (String line : lines) {
                        domainList.add(json2Object(line, getDomainClass()));
                    }
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
        List<T> domainList = new ArrayList<T>();
        final String domainName = getDomainClass().getSimpleName();
        if (!CollectionUtil.isEmpty(dbType)) {
            for (String sId : dbType) {
                String dbFileSuffix = '_' + sId + '_' + domainName + ".kdb";
                domainList.addAll(query(dbFileSuffix));
            }
            return domainList;
        }
        File dbDirFile = new File(dbDir);
        File[] dbFiles = dbDirFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(domainName + ".kdb");
            }
        });
        for (File dbFile : dbFiles) {
            String dbFileName = dbFile.getName();
            domainList.addAll(query(dbFileName));
        }
        return domainList;
    }

    /**
     * 删除
     *
     * @param domains 待删除集合。支持一个或多个
     * @return 成功删除记录数
     */
    @SuppressWarnings("unchecked")
    public int delete(Object... domains) {
        if (CollectionUtil.isEmpty(domains)) {
            return 0;
        }
        Map<String, List<T>> domainMap = new HashMap<String, List<T>>();
        int updateIndex = -1;
        int successCount = 0;
        for (Object domain1 : domains) {
            T domain = (T) domain1;
            String backDbFileName = domain.backDbFileName();
            List<T> domainList = domainMap.get(backDbFileName);
            if (domainList == null) {
                domainList = query(domain.dbFileName());
                if (CollectionUtil.isEmpty(domainList)) {
                    continue;
                }
                domainMap.put(backDbFileName, domainList);
            }
            for (int j = 0; j < domainList.size(); j++) {
                if (domain.equals(domainList.get(j))) {
                    updateIndex = j;
                    break;
                }
            }
            if (updateIndex > -1) {
                domainList.remove(updateIndex);
                updateIndex = -1;
                successCount++;
            }
        }
        for (Map.Entry<String, List<T>> item : domainMap.entrySet()) {
            List<T> domainList = item.getValue();
            List<String> eJsonList = new ArrayList<String>();
            for (Object domain : domainList) {
                eJsonList.add(object2Json(domain));
            }
            try {
                writeLines(dbDir + File.separatorChar + item.getKey(), eJsonList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return successCount;
    }
}
