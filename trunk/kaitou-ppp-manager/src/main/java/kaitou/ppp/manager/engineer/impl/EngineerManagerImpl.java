package kaitou.ppp.manager.engineer.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.shop.CachedShopDao;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.shop.CachedShopDetail;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.engineer.EngineerManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 工程师事务管理实现.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 11:14
 */
public class EngineerManagerImpl extends BaseFileDaoManager<Engineer> implements EngineerManager, ShopUpdateListener {

    private CachedShopDao cachedShopDao;

    public void setCachedShopDao(CachedShopDao cachedShopDao) {
        this.cachedShopDao = cachedShopDao;
    }

    @Override
    public Class<Engineer> domainClass() {
        return Engineer.class;
    }

    @Override
    public String getEntityName() {
        return domainClass().getSimpleName();
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        if (CollectionUtil.isEmpty(shops)) {
            return;
        }
        List<Engineer> engineers = new ArrayList<Engineer>();
        for (Shop shop : shops) {
            List<Engineer> list = query(shop.getId());
            if (CollectionUtil.isEmpty(list)) {
                continue;
            }
            String shopName = shop.getName();
            String saleRegion = shop.getSaleRegion();
            for (Engineer engineer : list) {
                engineer.setSaleRegion(saleRegion);
                engineer.setShopName(shopName);
                if (SysCode.ShopStatus.IN_THE_USE.getValue().equals(shop.getStatus())) {
                    continue;
                }
                engineer.setStatus(SysCode.EngineerStatus.OFF.getValue());
            }
            engineers.addAll(list);
        }
        save(engineers);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {
        if (CollectionUtil.isEmpty(shopDetails)) {
            return;
        }
        List<Engineer> engineers = new ArrayList<Engineer>();
        for (ShopDetail shopDetail : shopDetails) {
            List<Engineer> list = query(shopDetail.getId());
            if (CollectionUtil.isEmpty(list)) {
                continue;
            }
            CachedShopDetail cachedShopDetail;
            for (Engineer engineer : list) {
                cachedShopDetail = cachedShopDao.getCachedShopDetail(engineer.getShopId(), engineer.getProductLine());
                engineer.setShopLevel(cachedShopDetail.getLevel());
                engineer.setNumberOfYear(cachedShopDetail.getNumberOfYear());
            }
            engineers.addAll(list);
        }
        save(engineers);
    }
}
