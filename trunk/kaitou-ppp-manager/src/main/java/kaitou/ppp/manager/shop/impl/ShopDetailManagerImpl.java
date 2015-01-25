package kaitou.ppp.manager.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.shop.ShopDetailDao;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.manager.FileDaoManager;
import kaitou.ppp.manager.shop.ShopDetailManager;

import java.util.List;

/**
 * 认定店明细DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 18:12
 */
public class ShopDetailManagerImpl extends FileDaoManager implements ShopDetailManager {

    private ShopDetailDao shopDetailDao;

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
}
