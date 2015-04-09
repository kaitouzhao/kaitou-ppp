package kaitou.ppp.dao.system;

import java.util.List;

/**
 * 远程注册表DAO.
 * User: 赵立伟
 * Date: 2015/3/17
 * Time: 18:43
 */
public interface RemoteRegistryDao {
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
