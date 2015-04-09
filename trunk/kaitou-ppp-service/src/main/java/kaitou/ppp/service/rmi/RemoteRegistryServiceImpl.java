package kaitou.ppp.service.rmi;

import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.rmi.service.RemoteRegistryService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * 远程注册表业务层实现层.
 * User: 赵立伟
 * Date: 2015/4/5
 * Time: 11:50
 */
public class RemoteRegistryServiceImpl extends UnicastRemoteObject implements RemoteRegistryService {

    private RemoteRegistryManager remoteRegistryManager;

    public void setRemoteRegistryManager(RemoteRegistryManager remoteRegistryManager) {
        this.remoteRegistryManager = remoteRegistryManager;
    }

    public RemoteRegistryServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<String> register(String ip) throws RemoteException {
        remoteRegistryManager.register(ip);
        return remoteRegistryManager.queryRegistryIps();
    }
}
