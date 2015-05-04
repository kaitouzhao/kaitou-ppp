package kaitou.ppp.dao.system.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.system.RemoteRegistryDao;
import kaitou.ppp.domain.system.RemoteRegistry;

import java.util.List;

/**
 * 远程注册表DAO实现.
 * User: 赵立伟
 * Date: 2015/3/17
 * Time: 18:44
 */
public class RemoteRegistryDaoImpl extends BaseDao<RemoteRegistry> implements RemoteRegistryDao {
    @Override
    public Class<RemoteRegistry> getDomainClass() {
        return RemoteRegistry.class;
    }

    @Override
    public void register(String ip) {
        List<RemoteRegistry> registries = queryRemoteRegistries();
        if (CollectionUtil.isEmpty(registries)) {
            RemoteRegistry registry = new RemoteRegistry().addRegistry(ip);
            registries.add(registry);
        } else {
            registries.get(0).addRegistry(ip);
        }
        save(CollectionUtil.toArray(registries, RemoteRegistry.class));
    }

    /**
     * 获取远程注册表
     *
     * @return 注册表
     */
    private List<RemoteRegistry> queryRemoteRegistries() {
        return query(new RemoteRegistry().dbFileSuffix());
    }

    @Override
    public List<String> queryRegistryIps() {
        List<RemoteRegistry> registries = queryRemoteRegistries();
        if (CollectionUtil.isEmpty(registries)) {
            return new RemoteRegistry().getRemoteIps();
        }
        return registries.get(0).getRemoteIps();
    }
}
