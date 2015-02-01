package kaitou.ppp.dao.engineer;

import kaitou.ppp.domain.engineer.EngineerTraining;

import java.util.List;

/**
 * 工程师培训信息DAO.
 * User: 赵立伟
 * Date: 2015/1/24
 * Time: 22:10
 */
public interface EngineerTrainingDao {
    /**
     * 添加/更新
     *
     * @param engineerTrainings 工程师培训信息集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... engineerTrainings);

    /**
     * 查询认定店的工程师培训信息
     *
     * @param shopId 认定店编码。如果为空，则获取全部认定店
     * @return 培训信息列表
     */
    public List<EngineerTraining> query(String... shopId);

    /**
     * 删除培训信息
     *
     * @param trainings 待删除集合。可以是单个，也可以是多个
     * @return 成功记录数
     */
    public int delete(Object... trainings);
}
