package kaitou.ppp.manager.engineer.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.engineer.EngineerTrainingDao;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.manager.engineer.EngineerTrainingManager;
import kaitou.ppp.manager.FileDaoManager;

import java.util.List;

/**
 * 工程师培训信息DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/24
 * Time: 22:55
 */
public class EngineerTrainingManagerImpl extends FileDaoManager implements EngineerTrainingManager {

    private EngineerTrainingDao engineerTrainingDao;

    public void setEngineerTrainingDao(EngineerTrainingDao engineerTrainingDao) {
        this.engineerTrainingDao = engineerTrainingDao;
    }

    @Override
    public int importEngineerTrainings(List<EngineerTraining> trainings) {
        return engineerTrainingDao.save(CollectionUtil.toArray(trainings, EngineerTraining.class));
    }

    @Override
    public List<EngineerTraining> query(String... shopId) {
        return engineerTrainingDao.query(shopId);
    }

    @Override
    public String getEntityName() {
        return EngineerTraining.class.getSimpleName();
    }

    @Override
    public int delete(Object... trainings) {
        return engineerTrainingDao.delete(trainings);
    }
}
