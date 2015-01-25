package kaitou.ppp.manager.shop;

import kaitou.ppp.domain.shop.Shop;

import java.util.List;

/**
 * 认定店DAO事务管理层.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:19
 */
public interface ShopManager {
    /**
     * 获取db文件目录
     *
     * @return 目录
     */
    public String getDbDir();

    /**
     * 导入认定店
     *
     * @param shops 认定店列表
     * @return 成功执行个数
     */
    public int importShops(List<Shop> shops);

    /**
     * 获取全部认定店
     *
     * @return 认定店列表
     */
    public List<Shop> query();
}
