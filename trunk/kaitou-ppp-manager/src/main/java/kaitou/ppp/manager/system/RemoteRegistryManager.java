package kaitou.ppp.manager.system;

import java.util.List;

/**
 * 远程注册表事务管理层.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 14:21
 */
public interface RemoteRegistryManager {
    /**
     * 获取db文件目录
     *
     * @return 目录
     */
    public String getDbDir();

    /**
     * 注册
     *
     * @param ip IP地址
     */
    public void register(String ip);

    /**
     * 获取已注册的ip列表
     *
     * @return ip列表
     */
    public List<String> queryRegistryIps();
}
