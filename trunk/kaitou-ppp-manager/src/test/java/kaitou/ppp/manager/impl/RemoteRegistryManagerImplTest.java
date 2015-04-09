package kaitou.ppp.manager.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 远程注册表事务管理层实现单元测试.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 14:25
 */
public class RemoteRegistryManagerImplTest extends AbstractManagerTest {

    private RemoteRegistryManager mockRemoteRegistryManager;

    @Override
    public String getDbDir() {
        return mockRemoteRegistryManager.getDbDir();
    }

    @Override
    public void initManager() {
        mockRemoteRegistryManager = ctx.getBean(RemoteRegistryManager.class);
    }

    @Test
    public void testRegister() throws Exception {
        assertTrue(CollectionUtil.isEmpty(mockRemoteRegistryManager.queryRegistryIps()));
        mockRemoteRegistryManager.register("127.0.0.1");
        mockRemoteRegistryManager.register("128.0.0.1");
        mockRemoteRegistryManager.register("127.0.0.1");
        List<String> registryIps = mockRemoteRegistryManager.queryRegistryIps();
        assertFalse(CollectionUtil.isEmpty(registryIps));
        assertEquals(2, registryIps.size());
        assertEquals("127.0.0.1", registryIps.get(0));
        assertEquals("128.0.0.1", registryIps.get(1));
    }
}
