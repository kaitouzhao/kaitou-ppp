package kaitou.ppp.dao.engineer.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.engineer.EngineerTrainingDao;
import kaitou.ppp.domain.engineer.EngineerTraining;

/**
 * 工程师培训信息DAO实现.
 * User: 赵立伟
 * Date: 2015/1/24
 * Time: 22:36
 */
public class EngineerTrainingDaoImpl extends BaseDao<EngineerTraining> implements EngineerTrainingDao {

    @Override
    public Class<EngineerTraining> getDomainClass() {
        return EngineerTraining.class;
    }
}
