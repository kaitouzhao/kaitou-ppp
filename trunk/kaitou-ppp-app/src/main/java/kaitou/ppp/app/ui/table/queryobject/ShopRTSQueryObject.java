package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.app.ui.table.IQueryObject;
import kaitou.ppp.domain.shop.ShopRTS;

/**
 * 认定店RTS查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 15:11
 */
public class ShopRTSQueryObject implements IQueryObject<ShopRTS> {
    @Override
    public String title() {
        return "查询认定店RTS";
    }

    @Override
    public String[] tableTitles() {
        return new String[]{"认定店编号", "认定店名称", "产品线", "RTS"};
    }

    @Override
    public String[] fieldNames() {
        return new String[]{"id", "name", "productLine", "rts"};
    }

    @Override
    public String[] queryFieldNames() {
        return new String[]{"id", "name", "productLine", "rts"};
    }

    @Override
    public String domainType() {
        return ShopRTS.class.getSimpleName();
    }

    @Override
    public String[] opNames() {
        return null;
    }

    @Override
    public String opFieldName() {
        return null;
    }

    @Override
    public int editableColumnStartIndex() {
        return 3;
    }

    @Override
    public String[] saveTitles() {
        return new String[]{"认定店编号", "认定店名称", "产品线", "RTS"};
    }

    @Override
    public String[] saveFieldNames() {
        return new String[]{"id", "name", "productLine", "rts"};
    }

    @Override
    public Class<ShopRTS> domainClass() {
        return ShopRTS.class;
    }
}
