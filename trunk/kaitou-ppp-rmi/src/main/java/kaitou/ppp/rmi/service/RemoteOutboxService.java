package kaitou.ppp.rmi.service;

import kaitou.ppp.domain.system.OutboxDBVersion;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * 远程待发箱服务.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 23:21
 */
@Deprecated
public interface RemoteOutboxService extends Remote {
    /**
     * 获取待发箱
     *
     * @param destIp 目标IP
     * @return 待发箱列表
     */
    public List<OutboxDBVersion> queryOutbox(String destIp) throws RemoteException;

    /**
     * 移除待发箱
     *
     * @param outboxDBVersions 待发箱列表
     */
    public void removeOutbox(List<OutboxDBVersion> outboxDBVersions) throws RemoteException;

    /**
     * 获取DB文件
     *
     * @param outboxDBVersions 待发箱列表
     * @return 待发箱与DB文件内容对应关系。key：待发箱；value：内容
     */
    public Map<OutboxDBVersion, List<String>> queryDBs(List<OutboxDBVersion> outboxDBVersions) throws RemoteException;
}
