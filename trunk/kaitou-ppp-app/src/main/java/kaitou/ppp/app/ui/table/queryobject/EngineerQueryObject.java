package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.app.ui.table.IQueryObject;
import kaitou.ppp.domain.engineer.Engineer;

/**
 * 工程师查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 10:22
 */
public class EngineerQueryObject implements IQueryObject<Engineer> {

    @Override
    public String title() {
        return "查询工程师基本信息";
    }

    @Override
    public String[] tableTitles() {
        return new String[]{"区域", "产品线", "在职状态", "认定店编码", "认定店名称", "认定店等级", "认定年限", "工程师编号", "工程师姓名", "ACE等级", "入职时间", "离职时间", "邮箱", "电话", "地址", "操作"};
    }

    @Override
    public String[] fieldNames() {
        return new String[]{"saleRegion", "productLine", "status", "shopId", "shopName", "shopLevel", "numberOfYear", "id", "name", "aceLevel", "dateOfEntry", "dateOfDeparture", "email", "phone", "address"};
    }

    @Override
    public String[] queryFieldNames() {
        return new String[]{"saleRegion", "productLine", "status", "shopId", "shopName", "id", "name"};
    }

    @Override
    public String domainType() {
        return Engineer.class.getSimpleName();
    }

    @Override
    public String[] opNames() {
        return new String[]{"离职", "在职"};
    }

    @Override
    public String opFieldName() {
        return "status";
    }

    @Override
    public int editableColumnStartIndex() {
        return 8;
    }

    @Override
    public String[] saveTitles() {
        return new String[]{"产品线", "在职状态", "认定店编码", "认定店名称", "工程师编号", "工程师姓名", "ACE等级", "入职时间", "离职时间", "邮箱", "电话", "地址"};
    }

    @Override
    public String[] saveFieldNames() {
        return new String[]{"productLine", "status", "shopId", "shopName", "id", "name", "aceLevel", "dateOfEntry", "dateOfDeparture", "email", "phone", "address"};
    }

    @Override
    public Class<Engineer> domainClass() {
        return Engineer.class;
    }
}
