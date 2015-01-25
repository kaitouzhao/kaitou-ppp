package kaitou.ppp.manager.engineer.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.engineer.EngineerDao;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.manager.engineer.EngineerManager;
import kaitou.ppp.manager.FileDaoManager;

import java.util.List;

/**
 * 工程师事务管理实现.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 11:14
 */
public class EngineerManagerImpl extends FileDaoManager implements EngineerManager {

    private EngineerDao engineerDao;

    public void setEngineerDao(EngineerDao engineerDao) {
        this.engineerDao = engineerDao;
    }

    @Override
    public String getEntityName() {
        return Engineer.class.getSimpleName();
    }

    @Override
    public int importEngineers(List<Engineer> engineers) {
        return engineerDao.save(CollectionUtil.toArray(engineers, Engineer.class));
    }

    @Override
    public List<Engineer> query(String... shopId) {
        return engineerDao.query(shopId);
    }
}
