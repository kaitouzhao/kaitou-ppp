package kaitou.ppp.dao.system.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.AbstractDaoTest;
import kaitou.ppp.dao.system.OutboxDBVersionDao;
import kaitou.ppp.domain.system.OutboxDBVersion;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * DB文件版本待发箱DAO实现单元测试.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 16:19
 */
public class OutboxDBVersionDaoImplTest extends AbstractDaoTest {

    private OutboxDBVersionDao outboxDBVersionDao;

    @Override
    public String getDbDir() {
        return "D:\\temp\\ppp\\test\\outbox";
    }

    @Override
    public void initManager() {
        outboxDBVersionDao = ctx.getBean(OutboxDBVersionDao.class);
    }

    @Test
    public void testAdd2Outbox() throws Exception {
        OutboxDBVersion outboxDBVersion1 = new OutboxDBVersion();
        outboxDBVersion1.setDestIp("192.168.1.1");
        outboxDBVersion1.setDbFileName("dbFile1.kdb");
        outboxDBVersion1.setLatestVersion(2);
        OutboxDBVersion outboxDBVersion2 = new OutboxDBVersion();
        outboxDBVersion2.setDestIp("192.168.1.2");
        outboxDBVersion2.setDbFileName("dbFile1.kdb");
        outboxDBVersion2.setLatestVersion(1);
        List<OutboxDBVersion> outboxDBVersions = CollectionUtil.newList(outboxDBVersion1, outboxDBVersion2);
        outboxDBVersionDao.add2Outbox(outboxDBVersions);
        List<OutboxDBVersion> destOutbox = outboxDBVersionDao.queryOutbox("192.168.1.1");
        assertEquals(2, destOutbox.get(0).getLatestVersion());
        destOutbox = outboxDBVersionDao.queryOutbox("192.168.1.2");
        assertEquals(1, destOutbox.get(0).getLatestVersion());
        outboxDBVersion2 = new OutboxDBVersion();
        outboxDBVersion2.setDestIp("192.168.1.2");
        outboxDBVersion2.setDbFileName("dbFile1.kdb");
        outboxDBVersion2.setLatestVersion(4);
        outboxDBVersionDao.add2Outbox(CollectionUtil.newList(outboxDBVersion2));
        destOutbox = outboxDBVersionDao.queryOutbox("192.168.1.2");
        assertEquals(4, destOutbox.get(0).getLatestVersion());
        outboxDBVersionDao.remove(CollectionUtil.newList(outboxDBVersion1));
        assertTrue(CollectionUtil.isEmpty(outboxDBVersionDao.queryOutbox("192.168.1.1")));
    }
}
