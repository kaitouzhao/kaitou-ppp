package kaitou.ppp.dao;

import kaitou.ppp.domain.EngineerTraining;

import java.util.List;

/**
 * 工程师DAO.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 10:59
 */
public interface EngineerDao<Engineer, String> {
    /**
     * 添加/更新
     *
     * @param engineers 工程师集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... engineers);


    /**
     * 获取认定点全部工程师
     *
     * @param companyName 认定点名
     * @return 工程师列表
     */
    public List<Engineer> query(final String companyName);

    /**
     * 更新工程师培训信息
     *
     * @param trainings 培训信息列表
     * @return 成功更新工程师记录数
     */
    public int updateTrainings(List<EngineerTraining> trainings);

    /**
     * 提交
     */
    public void commit();

    /**
     * 回滚
     */
    public void rollback();
}
