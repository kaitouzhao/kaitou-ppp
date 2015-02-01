package kaitou.ppp.dao.shop;

import kaitou.ppp.domain.shop.ShopRTS;

import java.util.List;

/**
 * 认定店RTS DAO.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:16
 */
public interface ShopRTSDao {

    /**
     * 添加/更新
     *
     * @param shopRTSs 认定店集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... shopRTSs);

    /**
     * 查询认定店RTS信息
     *
     * @return RTS列表
     */
    public List<ShopRTS> query();

    /**
     * 删除
     *
     * @param shopRTSs 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... shopRTSs);
}
