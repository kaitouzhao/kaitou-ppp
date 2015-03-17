package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.shop.ShopRTS;

/**
 * 认定店RTS查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 15:11
 */
public class ShopRTSQueryObject extends BaseQueryObject {

    @Override
    public Class<ShopRTS> domainClass() {
        return ShopRTS.class;
    }
}
