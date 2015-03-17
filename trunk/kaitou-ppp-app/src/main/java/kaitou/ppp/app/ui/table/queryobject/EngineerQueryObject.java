package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.engineer.Engineer;

/**
 * 工程师查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 10:22
 */
public class EngineerQueryObject extends BaseQueryObject {

    @Override
    public Class<Engineer> domainClass() {
        return Engineer.class;
    }
}
