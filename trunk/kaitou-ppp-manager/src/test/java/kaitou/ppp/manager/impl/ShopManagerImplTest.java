package kaitou.ppp.manager.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.manager.mock.domain.MockShop;
import kaitou.ppp.manager.shop.ShopManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 认定店DAO事务管理层实现单元测试.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:26
 */
public class ShopManagerImplTest extends AbstractManagerTest {

    private ShopManager mockShopManager;

    @Override
    public String getDbDir() {
        return mockShopManager.getDbDir();
    }

    @Override
    public void initManager() {
        mockShopManager = ctx.getBean(ShopManager.class);
    }

    @Test
    public void testImportShops() {
        List<Shop> shops = new ArrayList<Shop>();
        shops.add(new MockShop("华北", "SHOP0001", "认定店1", "测试1", "13810001000", "饮马井", "k@1.com"));
        mockShopManager.importShops(shops);
        shops.clear();
        shops.add(new MockShop("华北", "SHOP0001", "认定店1", "测试1", "13810001000", "饮马井", "k@1.com"));
        mockShopManager.importShops(shops);
        shops.clear();
        shops.add(new MockShop("华北", "SHOP0002", "认定店1", "测试1", "13810001000", "饮马井", "k@1.com"));
        mockShopManager.importShops(shops);
        testQuery();
    }

    private void testQuery() {
        List<Shop> shops = mockShopManager.query();
        assertFalse(CollectionUtil.isEmpty(shops));
        assertEquals(2, shops.size());
        Shop shop = shops.get(0);
        assertEquals("SHOP0001", shop.getId());
        assertEquals("13810001000", shop.getPhone());
    }
}
