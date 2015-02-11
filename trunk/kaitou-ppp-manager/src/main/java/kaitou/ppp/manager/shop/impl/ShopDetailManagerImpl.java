package kaitou.ppp.manager.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.shop.CachedShopDao;
import kaitou.ppp.dao.shop.ShopDetailDao;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.manager.FileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.shop.ShopDetailManager;

import java.util.List;

/**
 * 认定店明细DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 18:12
 */
public class ShopDetailManagerImpl extends FileDaoManager implements ShopDetailManager, ShopUpdateListener {

    protected ShopDetailDao shopDetailDao;
    private CachedShopDao cachedShopDao;

    public void setCachedShopDao(CachedShopDao cachedShopDao) {
        this.cachedShopDao = cachedShopDao;
    }

    public void setShopDetailDao(ShopDetailDao shopDetailDao) {
        this.shopDetailDao = shopDetailDao;
    }

    @Override
    public String getEntityName() {
        return ShopDetail.class.getSimpleName();
    }

    @Override
    public int importShopDetails(List<ShopDetail> shopDetails) {
        return shopDetailDao.save(CollectionUtil.toArray(shopDetails, ShopDetail.class));
    }

    @Override
    public List<ShopDetail> query(String... numberOfYear) {
        return shopDetailDao.query(numberOfYear);
    }

    @Override
    public void cacheShop() {
        List<ShopDetail> details = query();
        cachedShopDao.updateShopDetails(CollectionUtil.toArray(details, ShopDetail.class));
    }

    @Override
    public int delete(Object... shopDetails) {
        return shopDetailDao.delete(shopDetails);
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        if (CollectionUtil.isEmpty(shops)) {
            return;
        }
        List<ShopDetail> details = query();
        for (ShopDetail detail : details) {
            for (Shop shop : shops) {
                if (!shop.getId().equals(detail.getId())) {
                    continue;
                }
                detail.setSaleRegion(shop.getSaleRegion());
                detail.setName(shop.getName());
            }
        }
        importShopDetails(details);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {
        cachedShopDao.updateShopDetails(shopDetails);
    }
}
