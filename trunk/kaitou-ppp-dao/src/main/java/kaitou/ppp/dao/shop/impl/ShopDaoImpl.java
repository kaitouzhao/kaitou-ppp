package kaitou.ppp.dao.shop.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.shop.ShopDao;
import kaitou.ppp.domain.shop.Shop;

import java.util.List;

/**
 * 认定店DAO实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:14
 */
public class ShopDaoImpl extends BaseDao<Shop> implements ShopDao {
    @Override
    public Class<Shop> getDomainClass() {
        return Shop.class;
    }

    @Override
    public List<Shop> query() {
        return super.query();
    }
}
