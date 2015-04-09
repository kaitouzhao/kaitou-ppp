package kaitou.ppp.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 远程注册表业务层.
 * User: 赵立伟
 * Date: 2015/4/5
 * Time: 11:48
 */
public interface RemoteRegistryService extends Remote {

    /**
     * 注册到主机
     *
     * @param ip IP地址
     * @return 已注册的IP列表
     */
    public List<String> register(String ip) throws RemoteException;

}
