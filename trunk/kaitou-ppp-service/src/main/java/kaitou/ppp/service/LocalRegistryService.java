package kaitou.ppp.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 本地注册表业务层.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 15:50
 */
public interface LocalRegistryService {

    /**
     * 更新注册表
     *
     * @param ipList IP列表
     */
    public void updateRegistryIps(List<String> ipList);

    /**
     * 获取已注册的IP列表
     *
     * @return IP列表
     */
    public List<String> queryRegistryIps();
}
