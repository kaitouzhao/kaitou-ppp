package kaitou.ppp.manager.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.shop.ShopRTSDao;
import kaitou.ppp.domain.shop.ShopRTS;
import kaitou.ppp.manager.FileDaoManager;
import kaitou.ppp.manager.shop.ShopRTSManager;

import java.util.List;

/**
 * 认定店RTS信息DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:31
 */
public class ShopRTSManagerImpl extends FileDaoManager implements ShopRTSManager {

    private ShopRTSDao shopRTSDao;

    public void setShopRTSDao(ShopRTSDao shopRTSDao) {
        this.shopRTSDao = shopRTSDao;
    }

    @Override
    public String getEntityName() {
        return ShopRTS.class.getSimpleName();
    }

    @Override
    public int importShops(List<ShopRTS> shopRTSs) {
        return shopRTSDao.save(CollectionUtil.toArray(shopRTSs, ShopRTS.class));
    }

    @Override
    public List<ShopRTS> query() {
        return shopRTSDao.query();
    }

    @Override
    public int delete(Object... shopRTSs) {
        return shopRTSDao.delete(shopRTSs);
    }
}
