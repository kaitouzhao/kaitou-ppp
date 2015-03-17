package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.shop.ShopDetail;

/**
 * 认定店级别查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 14:56
 */
public class ShopDetailQueryObject extends BaseQueryObject {

    @Override
    public Class<ShopDetail> domainClass() {
        return ShopDetail.class;
    }
}
