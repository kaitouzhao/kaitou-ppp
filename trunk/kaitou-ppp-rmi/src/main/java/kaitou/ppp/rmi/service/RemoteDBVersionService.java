package kaitou.ppp.rmi.service;

import kaitou.ppp.domain.system.DBVersion;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * 远程DB版本库服务.
 * User: 赵立伟
 * Date: 2015/4/11
 * Time: 13:42
 */
public interface RemoteDBVersionService extends Remote {
    /**
     * 查询远程版本库
     *
     * @return DB版本库
     */
    public List<DBVersion> queryRemoteDBVersions() throws RemoteException;

    /**
     * 获取远程待升级DB
     *
     * @param toUpgradeList 待升级DB版本库
     * @return 待升级DB与版本库关系。key：版本库；value：DB文件内容
     */
    public Map<DBVersion, List<String>> queryRemoteDBs(List<DBVersion> toUpgradeList) throws RemoteException;
}
