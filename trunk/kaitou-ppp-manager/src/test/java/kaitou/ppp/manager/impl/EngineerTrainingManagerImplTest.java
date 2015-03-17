package kaitou.ppp.manager.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.manager.engineer.EngineerTrainingManager;
import kaitou.ppp.manager.mock.domain.MockEngineerTraining;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 工程师培训信息DAO事务管理层实现单元测试.
 * User: 赵立伟
 * Date: 2015/1/24
 * Time: 23:03
 */
public class EngineerTrainingManagerImplTest extends AbstractManagerTest {

    private EngineerTrainingManager mockEngineerTrainingManager;

    private static final String SHOP_ID = "shop001";

    @Override
    public String getDbDir() {
        return mockEngineerTrainingManager.getDbDir();
    }

    @Override
    public void initManager() {
        mockEngineerTrainingManager = ctx.getBean(EngineerTrainingManager.class);
    }

    @Test
    public void testImportEngineerTrainings() throws Exception {
        List<EngineerTraining> trainings = new ArrayList<EngineerTraining>();
        trainings.add(new MockEngineerTraining("001", "测试3", SysCode.SaleRegion.NORTH_CHINA.getValue(), "认定店1", SysCode.ShopLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue(), "AGP", "测试", "正常", "2013/1/1", "M", SHOP_ID));
        mockEngineerTrainingManager.save(trainings);
        trainings.add(new MockEngineerTraining("001", "测试3", SysCode.SaleRegion.NORTH_CHINA.getValue(), "认定店1", SysCode.ShopLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue(), "AGP", "测试", "正常", "2013/1/1", "M", SHOP_ID));
        mockEngineerTrainingManager.save(trainings);
        trainings.add(new MockEngineerTraining("001", "测试3", SysCode.SaleRegion.NORTH_CHINA.getValue(), "认定店1", SysCode.ShopLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue(), "AGP", "测试", "正常", "2013/2/1", "M", SHOP_ID));
        mockEngineerTrainingManager.save(trainings);
        testQuery();
    }

    public void testQuery() throws Exception {
        List<EngineerTraining> trainings = mockEngineerTrainingManager.query(SHOP_ID);
        assertFalse(CollectionUtil.isEmpty(trainings));
        assertEquals(2, trainings.size());
        assertEquals(2, mockEngineerTrainingManager.query().size());
    }
}
