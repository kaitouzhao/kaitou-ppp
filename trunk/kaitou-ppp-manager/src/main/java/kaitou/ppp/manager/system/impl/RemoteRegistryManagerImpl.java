package kaitou.ppp.manager.system.impl;

import kaitou.ppp.dao.system.RemoteRegistryDao;
import kaitou.ppp.domain.system.RemoteRegistry;
import kaitou.ppp.manager.FileDaoManager;
import kaitou.ppp.manager.system.RemoteRegistryManager;

import java.util.List;

/**
 * 远程注册表事务管理层实现.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 14:22
 */
public class RemoteRegistryManagerImpl extends FileDaoManager implements RemoteRegistryManager {

    private RemoteRegistryDao remoteRegistryDao;

    public void setRemoteRegistryDao(RemoteRegistryDao remoteRegistryDao) {
        this.remoteRegistryDao = remoteRegistryDao;
    }

    @Override
    public String getEntityName() {
        return RemoteRegistry.class.getSimpleName();
    }

    @Override
    public void register(String ip) {
        remoteRegistryDao.register(ip);
    }

    @Override
    public List<String> queryRegistryIps() {
        return remoteRegistryDao.queryRegistryIps();
    }
}
