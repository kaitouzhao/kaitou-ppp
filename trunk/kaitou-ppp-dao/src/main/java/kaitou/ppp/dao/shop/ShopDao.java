package kaitou.ppp.dao.shop;

import kaitou.ppp.domain.shop.Shop;

import java.util.List;

/**
 * 认定店DAO.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:11
 */
public interface ShopDao {
    /**
     * 添加/更新
     *
     * @param shops 认定店集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... shops);

    /**
     * 查询全部认定店
     *
     * @return 认定店列表
     */
    public List<Shop> query();

    /**
     * 删除
     *
     * @param shops 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... shops);
}
