package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.engineer.EngineerTraining;

/**
 * 工程师发展信息查询对象.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 15:21
 */
public class EngineerTrainingQueryObject extends BaseQueryObject {

    @Override
    public Class<EngineerTraining> domainClass() {
        return EngineerTraining.class;
    }
}
