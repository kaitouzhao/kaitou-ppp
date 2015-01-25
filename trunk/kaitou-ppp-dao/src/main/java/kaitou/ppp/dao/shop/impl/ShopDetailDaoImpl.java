package kaitou.ppp.dao.shop.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.shop.ShopDetailDao;
import kaitou.ppp.domain.shop.ShopDetail;

/**
 * 认定店明细DAO实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 18:05
 */
public class ShopDetailDaoImpl extends BaseDao<ShopDetail> implements ShopDetailDao {
    @Override
    public Class<ShopDetail> getDomainClass() {
        return ShopDetail.class;
    }
}
