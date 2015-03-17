package kaitou.ppp.manager.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.shop.CachedShopDao;
import kaitou.ppp.dao.shop.ShopDao;
import kaitou.ppp.domain.shop.CachedShop;
import kaitou.ppp.domain.shop.CachedShopDetail;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.shop.ShopManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 认定店DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:22
 */
public class ShopManagerImpl extends BaseFileDaoManager<Shop> implements ShopManager, ShopUpdateListener {

    protected ShopDao shopDao;
    private CachedShopDao cachedShopDao;

    public void setCachedShopDao(CachedShopDao cachedShopDao) {
        this.cachedShopDao = cachedShopDao;
    }

    public void setShopDao(ShopDao shopDao) {
        this.shopDao = shopDao;
    }

    @Override
    public Class<Shop> domainClass() {
        return Shop.class;
    }

    @Override
    public String getEntityName() {
        return domainClass().getSimpleName();
    }

    @Override
    public List<Shop> query() {
        List<Shop> shops = super.query();
        Collections.sort(shops, new Comparator<Shop>() {
            @Override
            public int compare(Shop o1, Shop o2) {
                try {
                    return Long.valueOf(o1.getId().substring(3)).compareTo(Long.valueOf(o2.getId().substring(3)));
                } catch (Exception e) {
                    return 0;
                }
            }
        });
        return shops;
    }

    @Override
    public void cachedShop() {
        List<Shop> shops = query();
        cachedShopDao.updateShops(CollectionUtil.toArray(shops, Shop.class));
    }

    @Override
    public CachedShopDetail getCachedShopDetail(String shopId, String productLine) {
        return cachedShopDao.getCachedShopDetail(shopId, productLine);
    }

    @Override
    public CachedShop getCachedShop(String shopId) {
        return cachedShopDao.getCachedShop(shopId);
    }

    @Override
    public List<CachedShopDetail> queryCachedShopDetails(String shopId) {
        return cachedShopDao.queryCachedShopDetails(shopId);
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        cachedShopDao.updateShops(shops);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {
        // DO NOTHING
    }
}
