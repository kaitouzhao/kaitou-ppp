package kaitou.ppp.manager.system;

import kaitou.ppp.domain.system.OutboxDBVersion;

import java.util.List;

/**
 * DB文件版本待发箱事务控制层.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 22:30
 */
public interface OutboxDBVersionManager {
    /**
     * 获取目标IP的待发箱
     *
     * @param destIp 目标IP
     * @return 待发箱
     */
    public List<OutboxDBVersion> queryOutbox(String destIp);

    /**
     * 加入待发箱
     *
     * @param outboxDBVersions 待发箱记录
     */
    public void add2Outbox(List<OutboxDBVersion> outboxDBVersions);

    /**
     * 移除待发箱
     *
     * @param outboxDBVersions 待发箱记录
     */
    public void remove(List<OutboxDBVersion> outboxDBVersions);
}
