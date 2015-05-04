package kaitou.ppp.manager.system.impl;

import kaitou.ppp.dao.system.OutboxDBVersionDao;
import kaitou.ppp.domain.system.OutboxDBVersion;
import kaitou.ppp.manager.FileDaoManager;
import kaitou.ppp.manager.system.OutboxDBVersionManager;

import java.util.List;

/**
 * DB文件版本待发箱事务控制层实现.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 22:46
 */
public class OutboxDBVersionManagerImpl extends FileDaoManager implements OutboxDBVersionManager {

    private OutboxDBVersionDao outboxDBVersionDao;

    public void setOutboxDBVersionDao(OutboxDBVersionDao outboxDBVersionDao) {
        this.outboxDBVersionDao = outboxDBVersionDao;
    }

    @Override
    public String getEntityName() {
        return OutboxDBVersion.class.getSimpleName();
    }

    @Override
    public List<OutboxDBVersion> queryOutbox(String destIp) {
        return outboxDBVersionDao.queryOutbox(destIp);
    }

    @Override
    public void add2Outbox(List<OutboxDBVersion> outboxDBVersions) {
        outboxDBVersionDao.add2Outbox(outboxDBVersions);
    }

    @Override
    public void remove(List<OutboxDBVersion> outboxDBVersions) {
        outboxDBVersionDao.remove(outboxDBVersions);
    }
}
