package kaitou.ppp.manager.impl;

import kaitou.ppp.domain.shop.ShopRTS;
import kaitou.ppp.manager.mock.domain.MockShopRTS;
import kaitou.ppp.manager.shop.ShopRTSManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 认定店RTS信息DAO事务管理层实现单元测试.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:41
 */
public class ShopRTSManagerImplTest extends AbstractManagerTest {

    private ShopRTSManager mockShopRTSManager;

    @Override
    public String getDbDir() {
        return mockShopRTSManager.getDbDir();
    }

    @Override
    public void initManager() {
        mockShopRTSManager = ctx.getBean(ShopRTSManager.class);
    }

    @Test
    public void testImportShops() {
        List<ShopRTS> shopRTSes = new ArrayList<ShopRTS>();
        shopRTSes.add(new MockShopRTS("SHOP0001", "测试店1", "M1", "测试1"));
        mockShopRTSManager.save(shopRTSes);
        shopRTSes.add(new MockShopRTS("SHOP0002", "测试店2", "M1", "测试1"));
        mockShopRTSManager.save(shopRTSes);
        shopRTSes.clear();
        shopRTSes.add(new MockShopRTS("SHOP0001", "测试店2", "M1", "测试2"));
        mockShopRTSManager.save(shopRTSes);
        testQuery();
    }

    private void testQuery() {
        List<ShopRTS> shopRTSes = mockShopRTSManager.query();
        assertEquals(2, shopRTSes.size());
        assertEquals("测试2", shopRTSes.get(0).getRts());
    }
}
