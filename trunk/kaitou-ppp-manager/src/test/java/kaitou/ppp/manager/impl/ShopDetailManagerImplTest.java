package kaitou.ppp.manager.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.manager.mock.domain.MockShopDetail;
import kaitou.ppp.manager.shop.ShopDetailManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 认定店明细DAO事务管理层实现单元测试.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 18:16
 */
public class ShopDetailManagerImplTest extends AbstractManagerTest {

    private ShopDetailManager mockShopDetailManager;

    @Override
    public String getDbDir() {
        return mockShopDetailManager.getDbDir();
    }

    @Override
    public void initManager() {
        mockShopDetailManager = ctx.getBean(ShopDetailManager.class);
    }

    @Test
    public void testImportShopDetails() {
        List<ShopDetail> shopDetails = new ArrayList<ShopDetail>();
        shopDetails.add(new MockShopDetail("华北", "SHOP0001", "认定店1", "2013", "ABA", "金牌", "ABA"));
        shopDetails.add(new MockShopDetail("华北", "SHOP0001", "认定店1", "2014", "ABC", "金牌", "ABC"));
        mockShopDetailManager.save(shopDetails);
        shopDetails.clear();
        shopDetails.add(new MockShopDetail("华北", "SHOP0002", "认定店1", "2014", "DPO", "金牌", "ABC"));
        shopDetails.add(new MockShopDetail("华北", "SHOP0001", "认定店1", "2014", "ABC", "银牌", "ABC"));
        mockShopDetailManager.save(shopDetails);
        testQuery();
    }

    private void testQuery() {
        List<ShopDetail> shopDetails = mockShopDetailManager.query("2014");
        assertFalse(CollectionUtil.isEmpty(shopDetails));
        assertEquals(2, shopDetails.size());
        ShopDetail shopDetail = shopDetails.get(1);
        assertEquals("SHOP0002", shopDetail.getId());
        assertEquals("金牌", shopDetail.getLevel());
        assertEquals("DPO", shopDetail.getProductLine());
    }
}
