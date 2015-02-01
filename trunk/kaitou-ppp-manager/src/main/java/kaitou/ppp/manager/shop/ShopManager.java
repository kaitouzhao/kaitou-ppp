package kaitou.ppp.manager.shop;

import kaitou.ppp.domain.shop.CachedShopDetail;
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

    /**
     * 删除
     *
     * @param shops 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... shops);

    /**
     * 缓存全部认定店
     */
    public void cachedShop();

    /**
     * 获取缓存的认定店产品线最近发展信息
     *
     * @param shopId      认定店编码
     * @param productLine 产品线
     * @return 最近的发展信息
     */
    public CachedShopDetail getCachedShopDetail(String shopId, String productLine);

    /**
     * 获取缓存的认定店所有产品线最近发展信息
     *
     * @param shopId 认定店编码
     * @return 最近的发展信息
     */
    public List<CachedShopDetail> queryCachedShopDetails(String shopId);
}
