package kaitou.ppp.service.rmi;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.system.DBVersion;
import kaitou.ppp.manager.system.LocalDBVersionManager;
import kaitou.ppp.rmi.service.RemoteDBVersionService;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kaitou.ppp.common.log.BaseLogManager.logSystemEx;
import static kaitou.ppp.common.utils.FileUtil.readLines;

/**
 * 远程DB版本库服务实现.
 * User: 赵立伟
 * Date: 2015/4/11
 * Time: 13:46
 */
public class RemoteDBVersionServiceImpl extends UnicastRemoteObject implements RemoteDBVersionService {

    private String dbDir;
    private LocalDBVersionManager localDBVersionManager;

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public void setLocalDBVersionManager(LocalDBVersionManager localDBVersionManager) {
        this.localDBVersionManager = localDBVersionManager;
    }

    public RemoteDBVersionServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<DBVersion> queryRemoteDBVersions() throws RemoteException {
        return localDBVersionManager.queryDBVersions();
    }

    @Override
    public Map<DBVersion, List<String>> queryRemoteDBs(List<DBVersion> toUpgradeList) throws RemoteException {
        Map<DBVersion, List<String>> dbMap = new HashMap<DBVersion, List<String>>();
        if (CollectionUtil.isEmpty(toUpgradeList)) {
            return dbMap;
        }
        for (DBVersion dbVersion : toUpgradeList) {
            try {
                dbMap.put(dbVersion, readLines(dbDir + File.separatorChar + dbVersion.getDbFileName()));
            } catch (IOException e) {
                logSystemEx(e);
            }
        }
        return dbMap;
    }
}
