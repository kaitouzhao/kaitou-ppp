package kaitou.ppp.manager.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.manager.engineer.EngineerManager;
import kaitou.ppp.manager.mock.domain.MockEngineer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 工程师DAO事务管理单元测试.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 20:06
 */
public class EngineerManagerImplTest extends AbstractManagerTest {

    private EngineerManager mockEngineerManager;

    private static final String SHOP_ID = "shop001";

    @Override
    public String getDbDir() {
        return mockEngineerManager.getDbDir();
    }

    @Override
    public void initManager() {
        mockEngineerManager = ctx.getBean(EngineerManager.class);
    }

    @Test
    public void testImportEngineers() {
        List<Engineer> engineers = new ArrayList<Engineer>();
        String shopName = "认定店1";
        engineers.add(new MockEngineer("001", "测试1", SysCode.SaleRegion.NORTH_CHINA.getValue(), shopName, SysCode.ShopLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue(), SHOP_ID, "AGP", "", "", ""));
        engineers.add(new MockEngineer("002", "测试2", SysCode.SaleRegion.NORTH_CHINA.getValue(), "认定点2", SysCode.ShopLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue(), "shop002", "AGP", "", "", ""));
        mockEngineerManager.save(engineers);
        engineers.clear();
        engineers.add(new MockEngineer("001", "测试3", SysCode.SaleRegion.NORTH_CHINA.getValue(), shopName, SysCode.ShopLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue(), SHOP_ID, "AGP", "", "", ""));
        mockEngineerManager.save(engineers);
        engineers.add(new MockEngineer("001", "测试3", SysCode.SaleRegion.NORTH_CHINA.getValue(), shopName, SysCode.ShopLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue(), SHOP_ID, "DPO", "kid@1.com", "13810001000", "饮马井"));
        mockEngineerManager.save(engineers);
        testQuery();
        testDelete();
    }

    private void testQuery() {
        List<Engineer> engineers = mockEngineerManager.query(SHOP_ID);
        assertFalse(CollectionUtil.isEmpty(engineers));
        assertEquals(2, engineers.size());
        Engineer engineer = engineers.get(1);
        assertEquals("DPO", engineer.getProductLine());
        assertEquals("kid@1.com", engineer.getEmail());
        assertEquals("13810001000", engineer.getPhone());
        assertEquals("饮马井", engineer.getAddress());
        assertEquals(3, mockEngineerManager.query().size());
    }

    private void testDelete() {
        MockEngineer deleted = new MockEngineer();
        deleted.setSaleRegion(SysCode.SaleRegion.NORTH_CHINA.getValue());
        deleted.setShopId(SHOP_ID);
        deleted.setId("001");
        deleted.setProductLine("wwe");
        assertEquals(0, mockEngineerManager.delete(deleted));
        assertEquals(2, mockEngineerManager.query(SHOP_ID).size());
        deleted.setProductLine("DPO");
        assertEquals(1, mockEngineerManager.delete(deleted));
        assertEquals(1, mockEngineerManager.query(SHOP_ID).size());
    }
}
