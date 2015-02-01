package kaitou.ppp.dao.shop;

import kaitou.ppp.domain.shop.ShopDetail;

import java.util.List;

/**
 * 认定店明细DAO.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 18:01
 */
public interface ShopDetailDao {
    /**
     * 添加/更新
     *
     * @param shopDetails 认定店明细集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... shopDetails);

    /**
     * 查询认定店明细
     *
     * @param numberOfYear 认定年份，支持单年，或多年
     * @return 认定店明细列表
     */
    public List<ShopDetail> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param shopDetails 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... shopDetails);
}
