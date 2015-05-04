package kaitou.ppp.manager.impl;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

import static kaitou.ppp.common.utils.FileUtil.delete;

/**
 * 事务管理单元测试父类.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:28
 */
public abstract class AbstractManagerTest {

    private static final String TEST_LOCAL_DB_VERSION_FILE = "D:\\temp\\ppp\\test\\db_version\\LocalDBVersion.kdb";
    protected static ApplicationContext ctx;

    public abstract String getDbDir();

    public abstract void initManager();

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-manager-test.xml", "applicationContext-tx.xml"
                }
        );
        initManager();
    }

    @After
    public void clearDb() {
        File file = new File(getDbDir());
        File[] files = file.listFiles();
        for (File f : files) {
            delete(f.getPath());
        }
        delete(TEST_LOCAL_DB_VERSION_FILE);
    }
}
