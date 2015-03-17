package kaitou.ppp.manager.engineer;

import kaitou.ppp.domain.engineer.Engineer;

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
    public int save(List<Engineer> engineers);

    /**
     * 获取认定店工程师
     *
     * @param shopId 认定店编码。如果为空，则获取全部认定店
     * @return 工程师列表
     */
    public List<Engineer> query(String... shopId);

    /**
     * 删除
     *
     * @param engineers 待删除集合。支持一个或多个
     * @return 成功执行个数
     */
    public int delete(Object... engineers);
}
