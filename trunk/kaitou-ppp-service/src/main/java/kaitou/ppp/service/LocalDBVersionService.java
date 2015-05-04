package kaitou.ppp.service;

import kaitou.ppp.domain.system.DBVersion;

import java.util.List;
import java.util.Map;

/**
 * 本地DB版本库业务层.
 * User: 赵立伟
 * Date: 2015/4/11
 * Time: 9:11
 */
public interface LocalDBVersionService {
    /**
     * 筛选出待更新的DB
     *
     * @param remoteDbVersions 远程DB版本库
     * @return 待更新的DB版本库
     */
    public List<DBVersion> getToUpgradeList(List<DBVersion> remoteDbVersions);

    /**
     * 升级DB
     *
     * @param remoteDBs 远程DB版本库与DB文件内容关系
     */
    public void upgradeByRemoteDBs(Map<DBVersion, List<String>> remoteDBs);

    /**
     * 缓存DB版本
     */
    public void cacheDBLatestVersion();
}
