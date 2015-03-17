package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.shop.ShopPay;

/**
 * 认定店帐号信息查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 15:15
 */
public class ShopPayQueryObject extends BaseQueryObject {

    @Override
    public Class<ShopPay> domainClass() {
        return ShopPay.class;
    }
}
