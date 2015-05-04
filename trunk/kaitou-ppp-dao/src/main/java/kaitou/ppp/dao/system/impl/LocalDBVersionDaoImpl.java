package kaitou.ppp.dao.system.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.system.LocalDBVersionDao;
import kaitou.ppp.domain.system.DBVersion;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.womai.bsp.tool.utils.JsonUtil.object2Json;
import static kaitou.ppp.common.utils.FileUtil.writeLines;

/**
 * 本地DB版本库DAO实现.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 14:39
 */
public class LocalDBVersionDaoImpl extends BaseDao<DBVersion> implements LocalDBVersionDao {
    /**
     * 最新版本缓存。key：DB文件名；value：最新版本号
     */
    private static Map<String, Integer> cachedMap = new HashMap<String, Integer>();

    @Override
    public Class<DBVersion> getDomainClass() {
        return DBVersion.class;
    }

    @Override
    public void upgrade(List<String> toUpgradeDBList) {
        if (CollectionUtil.isEmpty(toUpgradeDBList)) {
            return;
        }
        Map<String, Integer> toUpgradeDBMap = new HashMap<String, Integer>();
        for (String dbFileName : toUpgradeDBList) {
            toUpgradeDBMap.put(dbFileName, -1);
        }
        upgrade(toUpgradeDBMap);
    }

    @Override
    public void upgrade(Map<String, Integer> toUpgradeDBMap) {
        if (CollectionUtil.isEmpty(toUpgradeDBMap)) {
            return;
        }
        List<DBVersion> newDbVersions = new ArrayList<DBVersion>();
        newDbVersions.addAll(queryDBVersions());
        DBVersion dbVersion;
        String latestModifyTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        for (Map.Entry<String, Integer> toUpgradeDB : toUpgradeDBMap.entrySet()) {
            dbVersion = new DBVersion();
            dbVersion.setDbFileName(toUpgradeDB.getKey());
            int index = newDbVersions.indexOf(dbVersion);
            if (index >= 0) {
                dbVersion = newDbVersions.get(index);
            } else {
                newDbVersions.add(dbVersion);
            }
            if (toUpgradeDB.getValue() > 0) {
                dbVersion.setLatestVersion(toUpgradeDB.getValue());
            } else {
                dbVersion.addVersion();
            }
            dbVersion.setLatestModifyTime(latestModifyTime);
        }
        List<String> lines = new ArrayList<String>();
        for (DBVersion version : newDbVersions) {
            lines.add(object2Json(version));
        }
        String dbFilePath = dbDir + File.separatorChar + new DBVersion().dbFileName();
        FileUtil.delete(dbFilePath);
        try {
            writeLines(dbFilePath, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cacheDBVersion(newDbVersions);
    }

    /**
     * 缓存DB版本库
     *
     * @param dbVersions DB版本库列表
     */
    private void cacheDBVersion(List<DBVersion> dbVersions) {
        for (DBVersion version : dbVersions) {
            cachedMap.put(version.getDbFileName(), version.getLatestVersion());
        }
    }

    @Override
    public void cacheDBLatestVersion() {
        cacheDBVersion(queryDBVersions());
    }

    @Override
    public int getDBLatestVersion(String dbFileName) {
        Integer version = cachedMap.get(dbFileName);
        return version == null ? 0 : version;
    }

    @Override
    public List<DBVersion> queryDBVersions() {
        return query(new DBVersion().dbFileSuffix());
    }
}
