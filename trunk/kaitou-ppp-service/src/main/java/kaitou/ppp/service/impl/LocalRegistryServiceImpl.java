package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.service.LocalRegistryService;

import java.util.List;

/**
 * 本地注册表业务层实现.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 15:52
 */
public class LocalRegistryServiceImpl implements LocalRegistryService {

    private RemoteRegistryManager remoteRegistryManager;

    public void setRemoteRegistryManager(RemoteRegistryManager remoteRegistryManager) {
        this.remoteRegistryManager = remoteRegistryManager;
    }

    @Override
    public void updateRegistryIps(List<String> ipList) {
        if (CollectionUtil.isEmpty(ipList)) {
            return;
        }
        for (String ip : ipList) {
            remoteRegistryManager.register(ip);
        }
    }

    @Override
    public List<String> queryRegistryIps() {
        return remoteRegistryManager.queryRegistryIps();
    }
}
