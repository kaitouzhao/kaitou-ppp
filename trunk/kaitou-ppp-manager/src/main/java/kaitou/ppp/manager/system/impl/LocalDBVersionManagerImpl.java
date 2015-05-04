package kaitou.ppp.manager.system.impl;

import kaitou.ppp.dao.system.LocalDBVersionDao;
import kaitou.ppp.domain.system.DBVersion;
import kaitou.ppp.manager.FileDaoManager;
import kaitou.ppp.manager.system.LocalDBVersionManager;

import java.util.List;
import java.util.Map;

/**
 * 本地DB版本库事务管理层实现.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 22:39
 */
public class LocalDBVersionManagerImpl extends FileDaoManager implements LocalDBVersionManager {

    private static final String LOCAL_VERSION_PREFIX = "Local";
    private LocalDBVersionDao localDBVersionDao;

    public void setLocalDBVersionDao(LocalDBVersionDao localDBVersionDao) {
        this.localDBVersionDao = localDBVersionDao;
    }

    @Override
    public String getEntityName() {
        return LOCAL_VERSION_PREFIX + getClass().getSimpleName();
    }

    @Override
    public void upgrade(Map<String, Integer> toUpgradeDBMap) {
        localDBVersionDao.upgrade(toUpgradeDBMap);
    }

    @Override
    public void cacheDBLatestVersion() {
        localDBVersionDao.cacheDBLatestVersion();
    }

    @Override
    public int getDBLatestVersion(String dbFileName) {
        return localDBVersionDao.getDBLatestVersion(dbFileName);
    }

    @Override
    public List<DBVersion> queryDBVersions() {
        return localDBVersionDao.queryDBVersions();
    }
}
