package kaitou.ppp.manager.system;

import kaitou.ppp.domain.system.DBVersion;

import java.util.List;
import java.util.Map;

/**
 * 本地DB版本库事务管理层.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 22:38
 */
public interface LocalDBVersionManager {
    /**
     * 升级DB版本
     *
     * @param toUpgradeDBMap 待升级的DB集合。key：DB文件名；value：升级到的版本号
     */
    public void upgrade(Map<String, Integer> toUpgradeDBMap);

    /**
     * 缓存DB版本
     */
    public void cacheDBLatestVersion();

    /**
     * 获取DB最新的版本号
     *
     * @param dbFileName DB文件名
     * @return 最新的版本号
     */
    public int getDBLatestVersion(String dbFileName);

    /**
     * 查询DB版本库
     *
     * @return DB版本库
     */
    public List<DBVersion> queryDBVersions();
}
