package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.app.ui.table.IQueryObject;
import kaitou.ppp.domain.shop.Shop;

/**
 * 认定店查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 10:25
 */
public class ShopQueryObject implements IQueryObject<Shop> {
    @Override
    public String title() {
        return "查询认定店基本信息";
    }

    @Override
    public String[] tableTitles() {
        return new String[]{"状态", "区域", "认定店编号", "认定店名称", "合同联系人", "联系电话", "邮寄地址", "联系邮箱", "操作"};
    }

    @Override
    public String[] fieldNames() {
        return new String[]{"status", "saleRegion", "id", "name", "linkMan", "phone", "address", "email"};
    }

    @Override
    public String[] queryFieldNames() {
        return new String[]{"saleRegion", "id", "name", "status"};
    }

    @Override
    public String domainType() {
        return Shop.class.getSimpleName();
    }

    @Override
    public String[] opNames() {
        return new String[]{"取消", "认定中"};
    }

    @Override
    public String opFieldName() {
        return "status";
    }

    @Override
    public int editableColumnStartIndex() {
        return 3;
    }

    @Override
    public String[] saveTitles() {
        return new String[]{"区域", "认定店编号", "认定店名称", "合同联系人", "联系电话", "邮寄地址", "联系邮箱"};
    }

    @Override
    public String[] saveFieldNames() {
        return new String[]{"saleRegion", "id", "name", "linkMan", "phone", "address", "email"};
    }

    @Override
    public Class<Shop> domainClass() {
        return Shop.class;
    }
}
