package kaitou.ppp.dao.shop.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.shop.ShopPayDao;
import kaitou.ppp.domain.shop.ShopPay;

import java.util.List;

/**
 * 认定店付款信息DAO实现.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:20
 */
public class ShopPayDaoImpl extends BaseDao<ShopPay> implements ShopPayDao {
    @Override
    public Class<ShopPay> getDomainClass() {
        return ShopPay.class;
    }

    @Override
    public List<ShopPay> query() {
        return super.query();
    }
}
