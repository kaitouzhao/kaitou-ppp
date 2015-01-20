package kaitou.ppp.manager.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.Engineer;
import kaitou.ppp.domain.EngineerTraining;
import kaitou.ppp.domain.SysCode;
import kaitou.ppp.manager.EngineerManager;
import kaitou.ppp.manager.mock.MockEngineer;
import kaitou.ppp.manager.mock.MockEngineerManagerImpl;
import kaitou.ppp.manager.mock.MockEngineerTraining;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static kaitou.ppp.common.utils.FileUtil.delete;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 工程师DAO事务管理单元测试.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 20:06
 */
public class EngineerManagerImplTest {

    private static ApplicationContext ctx;

    private EngineerManager mockEngineerManager;

    private static final String COMPANY_NAME = "认定点1";

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-manager-test.xml", "applicationContext-tx.xml"
                }
        );
        mockEngineerManager = ctx.getBean(EngineerManager.class);
    }

    @Test
    public void testImportEngineers() {
        List<Engineer> engineers = new ArrayList<Engineer>();
        engineers.add(new MockEngineer("001", "测试1", SysCode.SaleRegion.NORTH_CHINA.getValue(), COMPANY_NAME, SysCode.CompanyLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue()));
        engineers.add(new MockEngineer("002", "测试2", SysCode.SaleRegion.NORTH_CHINA.getValue(), "认定点2", SysCode.CompanyLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue()));
        mockEngineerManager.importEngineers(engineers);
        engineers.clear();
        engineers.add(new MockEngineer("001", "测试3", SysCode.SaleRegion.NORTH_CHINA.getValue(), COMPANY_NAME, SysCode.CompanyLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue()));
        mockEngineerManager.importEngineers(engineers);
        testQuery();
        testImportEngineerTrainings();
        testQuery1();
    }

    private void testQuery() {
        List<Engineer> engineers = mockEngineerManager.query(COMPANY_NAME);
        assertFalse(CollectionUtil.isEmpty(engineers));
        assertEquals(1, engineers.size());
    }

    private void testQuery1() {
        List<Engineer> engineers = mockEngineerManager.query(COMPANY_NAME);
        assertFalse(CollectionUtil.isEmpty(engineers));
        assertEquals(1, engineers.size());
        assertFalse(CollectionUtil.isEmpty(engineers.get(0).getEngineerTrainings()));
    }

    private void testImportEngineerTrainings() {
        List<EngineerTraining> trainings = new ArrayList<EngineerTraining>();
        trainings.add(new MockEngineerTraining("001", "测试3", SysCode.SaleRegion.NORTH_CHINA.getValue(), COMPANY_NAME, SysCode.CompanyLevel.GOLD.getValue(), "1", SysCode.ACELevel.FIRST.getValue(), "2013/1/1", "1", SysCode.EngineerStatus.ON.getValue(), "A", "测试", "", "2013/1/1", "M"));
        mockEngineerManager.importEngineerTrainings(trainings);
    }

    @After
    public void clearDb() {
        File file = new File(mockEngineerManager.getDbDir());
        File[] files = file.listFiles();
        for (File f : files) {
            delete(f.getPath());
        }
    }
}
