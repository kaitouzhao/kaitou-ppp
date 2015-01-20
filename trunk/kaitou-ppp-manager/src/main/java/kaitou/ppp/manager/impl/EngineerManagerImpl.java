package kaitou.ppp.manager.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.EngineerDao;
import kaitou.ppp.domain.Engineer;
import kaitou.ppp.domain.EngineerTraining;
import kaitou.ppp.manager.EngineerManager;
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

    private String dbDir;

    public void setEngineerDao(EngineerDao engineerDao) {
        this.engineerDao = engineerDao;
    }

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    @Override
    public String getDbDir() {
        return dbDir;
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
    public List<Engineer> query(String companyName) {
        return engineerDao.query(companyName);
    }

    @Override
    public int importEngineerTrainings(List<EngineerTraining> trainings) {
        return engineerDao.updateTrainings(trainings);
    }
}
