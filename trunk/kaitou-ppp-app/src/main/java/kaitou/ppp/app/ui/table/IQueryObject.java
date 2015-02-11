package kaitou.ppp.app.ui.table;

import kaitou.ppp.domain.BaseDomain;

/**
 * 查询对象接口.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 10:14
 */
public interface IQueryObject<T extends BaseDomain> {
    /**
     * 查询界面显示标题
     *
     * @return 标题
     */
    public String title();

    /**
     * table标题
     *
     * @return 标题
     */
    public String[] tableTitles();

    /**
     * 实体对应属性
     *
     * @return 属性
     */
    public String[] fieldNames();

    /**
     * 实体查询属性
     *
     * @return 属性
     */
    public String[] queryFieldNames();

    /**
     * 领域类型
     *
     * @return 类型
     */
    public String domainType();

    /**
     * 操作名
     * <p>只支持一组互斥操作。默认操作放首位</p>
     *
     * @return 操作名
     */
    public String[] opNames();

    /**
     * 操作属性
     * <p>只支持一组互斥操作</p>
     *
     * @return 操作属性
     */
    public String opFieldName();

    /**
     * 可编辑列号开始位置
     *
     * @return 列号开始位置。如果都不可编辑，则返回为-1
     */
    public int editableColumnStartIndex();

    /**
     * 保存属性标题集合
     *
     * @return 标题集合
     */
    public String[] saveTitles();

    /**
     * 保存属性名集合
     *
     * @return 属性名集合
     */
    public String[] saveFieldNames();

    /**
     * 实体class
     *
     * @return class
     */
    public Class<T> domainClass();
}
