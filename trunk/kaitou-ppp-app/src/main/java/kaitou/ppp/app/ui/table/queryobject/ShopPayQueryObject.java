package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.app.ui.table.IQueryObject;
import kaitou.ppp.domain.shop.ShopPay;

/**
 * 认定店帐号信息查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 15:15
 */
public class ShopPayQueryObject implements IQueryObject {
    @Override
    public String title() {
        return "查询认定店帐号信息";
    }

    @Override
    public String[] tableTitles() {
        return new String[]{"认定店编号", "认定店名称", "付款代码", "付款名称", "开户行", "帐号"};
    }

    @Override
    public String[] fieldNames() {
        return new String[]{"id", "name", "payCode", "payName", "accountBank", "accountNo"};
    }

    @Override
    public String[] queryFieldNames() {
        return new String[]{"id", "name", "payCode", "payName", "accountBank"};
    }

    @Override
    public String domainType() {
        return ShopPay.class.getSimpleName();
    }

    @Override
    public String[] opNames() {
        return null;
    }

    @Override
    public String opFieldName() {
        return null;
    }
}
