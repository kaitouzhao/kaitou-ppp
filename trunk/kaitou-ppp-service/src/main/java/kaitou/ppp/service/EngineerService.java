package kaitou.ppp.service;

import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;

import java.io.File;
import java.util.List;

/**
 * 工程师业务处理层.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 22:12
 */
public interface EngineerService {
    /**
     * 导入工程师
     *
     * @param srcFile 源文件
     */
    public void importEngineers(File srcFile);

    /**
     * 导出全部工程师
     *
     * @param targetFile 目标文件
     */
    public void exportEngineers(File targetFile);

    /**
     * 导入工程师培训信息
     *
     * @param srcFile 源文件
     */
    public void importEngineerTrainings(File srcFile);

    /**
     * 导出工程师全部培训信息
     *
     * @param targetFile 目标文件
     */
    public void exportTrainings(File targetFile);

    /**
     * 删除指定的工程师
     *
     * @param saleRegion  指定销售区域
     * @param shopId      指定认定店编码
     * @param id          指定工程师编码
     * @param productLine 指定工程师产品线
     */
    @Deprecated
    public void deleteEngineer(String saleRegion, String shopId, String id, String productLine);

    /**
     * 删除指定的工程师
     *
     * @param engineers 工程师集合
     */
    public void deleteEngineers(Object... engineers);

    /**
     * 删除指定的工程师发展信息
     *
     * @param saleRegion    指定销售区域
     * @param shopId        指定认定店编码
     * @param id            指定工程师编码
     * @param productLine   指定工程师产品线
     * @param trainingModel 培训机型
     */
    @Deprecated
    public void deleteEngineerTraining(String saleRegion, String shopId, String id, String productLine, String trainingModel);

    /**
     * 删除指定的工程师发展信息
     *
     * @param trainings 发展信息集合
     */
    public void deleteEngineerTrainings(Object... trainings);

    /**
     * 根据产品线统计在职工程师数
     *
     * @param targetFile 目标文件
     */
    public void countEngineersByProductLine(File targetFile);

    /**
     * 根据认定店信息统计在职工程师数
     *
     * @param productLine 产品线。为空默认全部
     * @param targetFile  目标文件
     */
    public void countEngineersByShop(String productLine, File targetFile);

    /**
     * 查询全部工程师
     *
     * @return 工程师列表
     */
    public List<Engineer> queryAllEngineers();

    /**
     * 查询全部工程师培训信息
     *
     * @return 培训信息列表
     */
    public List<EngineerTraining> queryAllTrainings();

    /**
     * 保存/更新工程师基本信息
     *
     * @param engineer 工程师
     */
    public void saveOrUpdateEngineer(Engineer engineer);

    /**
     * 保存/更新工程师发展信息
     *
     * @param training 发展信息
     */
    public void saveOrUpdateEngineerTraining(EngineerTraining training);
}
