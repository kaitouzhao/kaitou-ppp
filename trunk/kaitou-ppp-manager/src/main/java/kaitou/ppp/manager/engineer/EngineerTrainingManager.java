package kaitou.ppp.manager.engineer;

import kaitou.ppp.domain.engineer.EngineerTraining;

import java.util.List;

/**
 * 工程师培训信息DAO事务管理层.
 * User: 赵立伟
 * Date: 2015/1/24
 * Time: 22:53
 */
public interface EngineerTrainingManager {
    /**
     * 获取db文件目录
     *
     * @return 目录
     */
    public String getDbDir();

    /**
     * 导入工程师培训信息
     *
     * @param trainings 培训信息列表
     * @return 成功更新的工程师记录数
     */
    public int save(List<EngineerTraining> trainings);


    /**
     * 获取认定店工程师培训信息
     *
     * @param shopId 认定店编码。如果为空，则获取全部认定店
     * @return 工程师培训信息列表
     */
    public List<EngineerTraining> query(String... shopId);

    /**
     * 删除
     *
     * @param trainings 待删除集合。支持一个或多个
     * @return 成功执行个数
     */
    public int delete(Object... trainings);
}
