package kaitou.ppp.manager.impl;

import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.manager.mock.domain.MockShopPay;
import kaitou.ppp.manager.shop.ShopPayManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 认定店付款信息DAO事务管理层实现单元测试.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 22:03
 */
public class ShopPayManagerImplTest extends AbstractManagerTest {

    private ShopPayManager mockShopPayManager;

    @Test
    public void testImportShops() {
        List<ShopPay> shopPays = new ArrayList<ShopPay>();
        shopPays.add(new MockShopPay("SHOP0001", "测试店1", "01", "01", "中行", "a1"));
        mockShopPayManager.save(shopPays);
        shopPays.add(new MockShopPay("SHOP0002", "测试店2", "02", "02", "中行", "a2"));
        mockShopPayManager.save(shopPays);
        shopPays.clear();
        shopPays.add(new MockShopPay("SHOP0001", "测试店1", "03", "03", "中行", "a3"));
        mockShopPayManager.save(shopPays);
        testQuery();
    }

    private void testQuery() {
        List<ShopPay> shopPays = mockShopPayManager.query();
        assertEquals(2, shopPays.size());
        assertEquals("a3", shopPays.get(0).getAccountNo());
    }

    @Override
    public String getDbDir() {
        return mockShopPayManager.getDbDir();
    }

    @Override
    public void initManager() {
        mockShopPayManager = ctx.getBean(ShopPayManager.class);
    }
}
