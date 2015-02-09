package kaitou.ppp.dao.shop;

import kaitou.ppp.domain.shop.CachedShop;
import kaitou.ppp.domain.shop.CachedShopDetail;

import java.util.List;

/**
 * 认定店缓存DAO.
 * User: 赵立伟
 * Date: 2015/1/28
 * Time: 16:28
 */
public interface CachedShopDao {

    /**
     * 更新认定店基本信息
     *
     * @param shops 认定店列表
     */
    public void updateShops(Object... shops);

    /**
     * 更新认定店发展信息
     *
     * @param shopDetails 认定店列表
     */
    public void updateShopDetails(Object... shopDetails);

    /**
     * 获取缓存的认定店产品线最近发展信息
     *
     * @param shopId      认定店编码
     * @param productLine 产品线
     * @return 最近的发展信息
     */
    public CachedShopDetail getCachedShopDetail(String shopId, String productLine);

    /**
     * 获取缓存的认定店
     *
     * @param shopId 认定店编码
     * @return 认定店
     */
    public CachedShop getCachedShop(String shopId);

    /**
     * 获取缓存的认定店所有产品线最近发展信息
     *
     * @param shopId 认定店编码
     * @return 最近的发展信息
     */
    public List<CachedShopDetail> queryCachedShopDetails(String shopId);
}
