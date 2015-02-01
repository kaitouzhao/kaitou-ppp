package kaitou.ppp.dao.engineer;

import kaitou.ppp.domain.engineer.Engineer;

import java.util.List;

/**
 * 工程师DAO.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 10:59
 */
public interface EngineerDao {
    /**
     * 添加/更新
     *
     * @param engineers 工程师集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... engineers);

    /**
     * 获取认定店工程师
     *
     * @param shopId 认定店编码。如果为空，则获取全部认定店
     * @return 工程师列表
     */
    public List<Engineer> query(String... shopId);

    /**
     * 删除工程师
     *
     * @param engineers 待删除集合。可以是单个，也可以是多个
     * @return 成功记录数
     */
    public int delete(Object... engineers);
}
