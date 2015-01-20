package kaitou.ppp.manager;

import kaitou.ppp.domain.Engineer;
import kaitou.ppp.domain.EngineerTraining;

import java.util.List;

/**
 * 工程师事务管理.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 11:06
 */
public interface EngineerManager {
    /**
     * 获取db文件目录
     *
     * @return 目录
     */
    public String getDbDir();

    /**
     * 导入工程师
     *
     * @param engineers 工程师列表
     * @return 成功执行个数
     */
    public int importEngineers(List<Engineer> engineers);

    /**
     * 获取指定认定点的全部工程师
     *
     * @param companyName 指定认定点名
     * @return 工程师列表
     */
    public List<Engineer> query(String companyName);

    /**
     * 导入工程师培训信息
     *
     * @param trainings 培训信息列表
     * @return 成功更新的工程师记录数
     */
    public int importEngineerTrainings(List<EngineerTraining> trainings);
}
