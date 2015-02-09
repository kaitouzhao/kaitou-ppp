package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.app.ui.table.IQueryObject;
import kaitou.ppp.domain.engineer.EngineerTraining;

/**
 * 工程师发展信息查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 15:21
 */
public class EngineerTrainingQueryObject implements IQueryObject {
    @Override
    public String title() {
        return "查询工程师发展信息";
    }

    @Override
    public String[] tableTitles() {
        return new String[]{"区域", "产品线", "在职状态", "认定店编码", "认定店名称", "认定店等级", "认定年限", "工程师编号", "工程师姓名", "ACE等级", "入职时间", "离职时间", "培训师", "培训类型", "培训时间", "培训机型"};
    }

    @Override
    public String[] fieldNames() {
        return new String[]{"saleRegion", "productLine", "status", "shopId", "shopName", "shopLevel", "numberOfYear", "id", "name", "aceLevel", "dateOfEntry", "dateOfDeparture", "trainer", "trainingType", "dateOfTraining", "trainingModel"};
    }

    @Override
    public String[] queryFieldNames() {
        return new String[]{"saleRegion", "productLine", "status", "shopId", "shopName", "shopLevel", "id", "name", "aceLevel", "trainer"};
    }

    @Override
    public String domainType() {
        return EngineerTraining.class.getSimpleName();
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
