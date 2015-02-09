package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.app.ui.table.IQueryObject;
import kaitou.ppp.domain.shop.ShopDetail;

/**
 * 认定店级别查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 14:56
 */
public class ShopDetailQueryObject implements IQueryObject {
    @Override
    public String title() {
        return "查询认定店认定级别";
    }

    @Override
    public String[] tableTitles() {
        return new String[]{"区域", "认定店编号", "认定店名称", "认定年份", "产品线", "认定级别", "认定机型"};
    }

    @Override
    public String[] fieldNames() {
        return new String[]{"saleRegion", "id", "name", "numberOfYear", "productLine", "level", "model"};
    }

    @Override
    public String[] queryFieldNames() {
        return new String[]{"saleRegion", "id", "name", "numberOfYear", "productLine"};
    }

    @Override
    public String domainType() {
        return ShopDetail.class.getSimpleName();
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
