package kaitou.ppp.manager.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.shop.ShopPayDao;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.manager.FileDaoManager;
import kaitou.ppp.manager.shop.ShopPayManager;

import java.util.List;

/**
 * 认定店付款信息DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:34
 */
public class ShopPayManagerImpl extends FileDaoManager implements ShopPayManager {

    private ShopPayDao shopPayDao;

    public void setShopPayDao(ShopPayDao shopPayDao) {
        this.shopPayDao = shopPayDao;
    }

    @Override
    public String getEntityName() {
        return ShopPay.class.getSimpleName();
    }

    @Override
    public int importShops(List<ShopPay> shopPays) {
        return shopPayDao.save(CollectionUtil.toArray(shopPays, ShopPay.class));
    }

    @Override
    public List<ShopPay> query() {
        return shopPayDao.query();
    }

    @Override
    public int delete(Object... shopPays) {
        return shopPayDao.delete(shopPays);
    }
}
