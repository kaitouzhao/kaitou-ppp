package kaitou.ppp.dao.system.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.AbstractDaoTest;
import kaitou.ppp.dao.system.LocalDBVersionDao;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * 本地DB版本库DAO实现单元测试.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 15:10
 */
public class LocalDBVersionDaoImplTest extends AbstractDaoTest {

    private LocalDBVersionDao localDBVersionDao;

    @Override
    public String getDbDir() {
        return "D:\\temp\\ppp\\test\\local_db_version";
    }

    @Override
    public void initManager() {
        localDBVersionDao = ctx.getBean(LocalDBVersionDao.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUpgrade() throws Exception {
        String dbFileName1 = "dbFile1.kdb";
        String dbFileName2 = "dbFile2.kdb";
        List<String> toUpgradeDBList = CollectionUtil.newList(dbFileName1, dbFileName2);
        localDBVersionDao.upgrade(toUpgradeDBList);
        assertEquals(1, localDBVersionDao.getDBLatestVersion(dbFileName1));
        assertEquals(1, localDBVersionDao.getDBLatestVersion(dbFileName2));
        toUpgradeDBList = CollectionUtil.newList(dbFileName2);
        localDBVersionDao.upgrade(toUpgradeDBList);
        assertEquals(1, localDBVersionDao.getDBLatestVersion(dbFileName1));
        assertEquals(2, localDBVersionDao.getDBLatestVersion(dbFileName2));
        Map<String, Integer> toUpgradeDBMap = new HashMap<String, Integer>();
        toUpgradeDBMap.put(dbFileName1, 7);
        localDBVersionDao.upgrade(toUpgradeDBMap);
        assertEquals(7, localDBVersionDao.getDBLatestVersion(dbFileName1));
        assertEquals(2, localDBVersionDao.getDBLatestVersion(dbFileName2));
    }
}
