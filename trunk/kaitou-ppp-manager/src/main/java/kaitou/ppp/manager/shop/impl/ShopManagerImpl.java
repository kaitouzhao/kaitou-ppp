package kaitou.ppp.manager.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.shop.ShopDao;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.manager.FileDaoManager;
import kaitou.ppp.manager.shop.ShopManager;

import java.util.List;

/**
 * 认定店DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:22
 */
public class ShopManagerImpl extends FileDaoManager implements ShopManager {

    private ShopDao shopDao;

    public void setShopDao(ShopDao shopDao) {
        this.shopDao = shopDao;
    }

    @Override
    public String getEntityName() {
        return Shop.class.getSimpleName();
    }

    @Override
    public int importShops(List<Shop> shops) {
        return shopDao.save(CollectionUtil.toArray(shops, Shop.class));
    }

    @Override
    public List<Shop> query() {
        return shopDao.query();
    }
}
