package kaitou.ppp.dao.system.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.system.OutboxDBVersionDao;
import kaitou.ppp.domain.system.OutboxDBVersion;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.JsonUtil.object2Json;
import static kaitou.ppp.common.utils.FileUtil.writeLines;

/**
 * DB文件版本待发箱DAO实现.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 14:36
 */
public class OutboxDBVersionDaoImpl extends BaseDao<OutboxDBVersion> implements OutboxDBVersionDao {
    @Override
    public Class<OutboxDBVersion> getDomainClass() {
        return OutboxDBVersion.class;
    }

    @Override
    public List<OutboxDBVersion> queryOutbox(String destIp) {
        List<OutboxDBVersion> result = CollectionUtil.newList();
        List<OutboxDBVersion> outboxDBVersions = query();
        if (StringUtils.isEmpty(destIp) || CollectionUtil.isEmpty(outboxDBVersions)) {
            return result;
        }
        for (OutboxDBVersion outboxDBVersion : outboxDBVersions) {
            if (outboxDBVersion.getDestIp().equals(destIp)) {
                result.add(outboxDBVersion);
            }
        }
        return result;
    }

    @Override
    public void add2Outbox(List<OutboxDBVersion> outboxDBVersions) {
        List<OutboxDBVersion> allList = query();
        String latestModifyTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        OutboxDBVersion outboxDBVersion;
        for (OutboxDBVersion version : outboxDBVersions) {
            int index = allList.indexOf(version);
            if (index < 0) {
                version.setLatestModifyTime(latestModifyTime);
                allList.add(version);
            } else {
                outboxDBVersion = allList.get(index);
                outboxDBVersion.setLatestVersion(version.getLatestVersion());
                outboxDBVersion.setLatestModifyTime(latestModifyTime);
            }
        }
        save(allList);
    }

    /**
     * 保存
     *
     * @param outboxDBVersions 待发箱列表
     */
    private void save(List<OutboxDBVersion> outboxDBVersions) {
        String dbFilePath = dbDir + File.separatorChar + new OutboxDBVersion().dbFileName();
        FileUtil.delete(dbFilePath);
        List<String> lines = new ArrayList<String>();
        for (OutboxDBVersion outboxDBVersion : outboxDBVersions) {
            lines.add(object2Json(outboxDBVersion));
        }
        try {
            writeLines(dbFilePath, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(List<OutboxDBVersion> outboxDBVersions) {
        List<OutboxDBVersion> allList = query();
        if (CollectionUtil.isEmpty(outboxDBVersions) || CollectionUtil.isEmpty(allList)) {
            return;
        }
        allList.removeAll(outboxDBVersions);
        save(allList);
    }
}
