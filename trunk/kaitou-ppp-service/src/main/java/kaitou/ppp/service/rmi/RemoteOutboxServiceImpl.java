package kaitou.ppp.service.rmi;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.system.OutboxDBVersion;
import kaitou.ppp.manager.system.OutboxDBVersionManager;
import kaitou.ppp.rmi.service.RemoteOutboxService;

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
 * 远程待发箱服务实现.
 * User: 赵立伟
 * Date: 2015/4/11
 * Time: 9:24
 */
@Deprecated
public class RemoteOutboxServiceImpl extends UnicastRemoteObject implements RemoteOutboxService {

    private OutboxDBVersionManager outboxDBVersionManager;
    private String dbDir;

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public void setOutboxDBVersionManager(OutboxDBVersionManager outboxDBVersionManager) {
        this.outboxDBVersionManager = outboxDBVersionManager;
    }

    public RemoteOutboxServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<OutboxDBVersion> queryOutbox(String destIp) throws RemoteException {
        return outboxDBVersionManager.queryOutbox(destIp);
    }

    @Override
    public void removeOutbox(List<OutboxDBVersion> outboxDBVersions) throws RemoteException {
        outboxDBVersionManager.remove(outboxDBVersions);
    }

    @Override
    public Map<OutboxDBVersion, List<String>> queryDBs(List<OutboxDBVersion> outboxDBVersions) throws RemoteException {
        Map<OutboxDBVersion, List<String>> dbMap = new HashMap<OutboxDBVersion, List<String>>();
        if (CollectionUtil.isEmpty(outboxDBVersions)) {
            return dbMap;
        }
        for (OutboxDBVersion outboxDBVersion : outboxDBVersions) {
            try {
                dbMap.put(outboxDBVersion, readLines(dbDir + File.separatorChar + outboxDBVersion.getDbFileName()));
            } catch (IOException e) {
                logSystemEx(e);
            }
        }
        return dbMap;
    }
}
