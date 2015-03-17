package kaitou.ppp.dao.engineer.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.engineer.EngineerDao;
import kaitou.ppp.domain.engineer.Engineer;

/**
 * 工程师DAO实现.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 11:12
 */
public class EngineerDaoImpl extends BaseDao<Engineer> implements EngineerDao {

    @Override
    public Class<Engineer> getDomainClass() {
        return Engineer.class;
    }

}
