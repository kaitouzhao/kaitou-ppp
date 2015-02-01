package kaitou.ppp.dao.shop.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.shop.ShopRTSDao;
import kaitou.ppp.domain.shop.ShopRTS;

import java.util.List;

/**
 * 认定店RTS DAO实现.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:18
 */
public class ShopRTSDaoImpl extends BaseDao<ShopRTS> implements ShopRTSDao {
    @Override
    public Class<ShopRTS> getDomainClass() {
        return ShopRTS.class;
    }

    @Override
    public List<ShopRTS> query() {
        return super.query();
    }
}
