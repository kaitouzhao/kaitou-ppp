package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.app.ui.table.IQueryObject;

import static com.womai.bsp.tool.utils.PropertyUtil.getValue;

/**
 * 查询实体父类.
 * User: 赵立伟
 * Date: 2015/2/28
 * Time: 14:27
 */
public abstract class BaseQueryObject implements IQueryObject {

    private static final String SCHEME_PROPERTIES = "scheme.properties";

    @Override
    public String title() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_TITLE");
    }

    @Override
    public String[] tableTitles() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_TABLE_TITLES").split(",");
    }

    @Override
    public String[] fieldNames() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_FIELD_NAMES").split(",");
    }

    @Override
    public String[] queryFieldNames() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_QUERY_FIELDS").split(",");
    }

    @Override
    public String domainType() {
        return domainClass().getSimpleName();
    }

    @Override
    public String[] opNames() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_OP_NAMES").split(",");
    }

    @Override
    public String opFieldName() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_OP_FIELD_NAME");
    }

    @Override
    public int editableColumnStartIndex() {
        return Integer.valueOf(getValue(SCHEME_PROPERTIES, domainType() + "_EDITABLE_COLUMN_START_INDEX"));
    }

    @Override
    public String[] saveTitles() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_SAVE_TITLES").split(",");
    }

    @Override
    public String[] saveFieldNames() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_SAVE_FIELD_NAMES").split(",");
    }
}
