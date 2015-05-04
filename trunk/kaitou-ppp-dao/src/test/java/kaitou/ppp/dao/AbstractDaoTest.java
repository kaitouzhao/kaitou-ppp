package kaitou.ppp.dao;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

import static kaitou.ppp.common.utils.FileUtil.delete;

/**
 * DAO单元测试父类.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:28
 */
public abstract class AbstractDaoTest {

    protected static ApplicationContext ctx;

    public abstract String getDbDir();

    public abstract void initManager();

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-dao-test.xml"
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
    }
}
