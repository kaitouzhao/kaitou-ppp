package kaitou.ppp.app.ui.table;

/**
 * 查询对象接口.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 10:14
 */
public interface IQueryObject {
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
}
