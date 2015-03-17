package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.shop.Shop;

/**
 * 认定店查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 10:25
 */
public class ShopQueryObject extends BaseQueryObject {

    @Override
    public Class<Shop> domainClass() {
        return Shop.class;
    }
}
