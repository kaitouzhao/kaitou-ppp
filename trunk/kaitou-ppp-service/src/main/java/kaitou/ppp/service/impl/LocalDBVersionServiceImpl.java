package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.domain.system.DBVersion;
import kaitou.ppp.manager.system.LocalDBVersionManager;
import kaitou.ppp.service.LocalDBVersionService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kaitou.ppp.common.utils.FileUtil.*;

/**
 * 本地DB版本库业务层实现.
 * User: 赵立伟
 * Date: 2015/4/11
 * Time: 9:46
 */
public class LocalDBVersionServiceImpl extends BaseLogManager implements LocalDBVersionService {

    private String dbDir;
    private LocalDBVersionManager localDBVersionManager;

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public void setLocalDBVersionManager(LocalDBVersionManager localDBVersionManager) {
        this.localDBVersionManager = localDBVersionManager;
    }

    @Override
    public List<DBVersion> getToUpgradeList(List<DBVersion> remoteDbVersions) {
        List<DBVersion> toUpgradeList = new ArrayList<DBVersion>();
        if (CollectionUtil.isEmpty(remoteDbVersions)) {
            return toUpgradeList;
        }
        for (DBVersion remoteDBVersion : remoteDbVersions) {
            int remoteVersion = remoteDBVersion.getLatestVersion();
            String dbFileName = remoteDBVersion.getDbFileName();
            if (remoteVersion > localDBVersionManager.getDBLatestVersion(dbFileName)) {
                toUpgradeList.add(remoteDBVersion);
            }
        }
        return toUpgradeList;
    }

    @Override
    public void upgradeByRemoteDBs(Map<DBVersion, List<String>> remoteDBs) {
        if (CollectionUtil.isEmpty(remoteDBs)) {
            return;
        }
        Map<String, Integer> toUpgradeDBMap = new HashMap<String, Integer>();
        for (Map.Entry<DBVersion, List<String>> item : remoteDBs.entrySet()) {
            DBVersion remoteDBVersion = item.getKey();
            String dbFileName = remoteDBVersion.getDbFileName();
            String dbFilePath = dbDir + File.separatorChar + dbFileName;
            String backDbFilePath = dbFilePath + ".back";
            copy(dbFilePath, backDbFilePath);
            delete(dbFilePath);
            try {
                writeLines(dbFilePath, item.getValue());
                toUpgradeDBMap.put(dbFileName, remoteDBVersion.getLatestVersion());
                logOperation("已同步升级：" + dbFileName);
            } catch (IOException e) {
                logSystemEx(e);
                copy(backDbFilePath, dbFilePath);
            }
            delete(backDbFilePath);
        }
        if (CollectionUtil.isEmpty(toUpgradeDBMap)) {
            return;
        }
        localDBVersionManager.upgrade(toUpgradeDBMap);
    }

    @Override
    public void cacheDBLatestVersion() {
        localDBVersionManager.cacheDBLatestVersion();
    }
}
