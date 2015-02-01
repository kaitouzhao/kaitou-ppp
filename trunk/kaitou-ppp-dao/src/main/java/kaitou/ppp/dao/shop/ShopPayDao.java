package kaitou.ppp.dao.shop;

import kaitou.ppp.domain.shop.ShopPay;

import java.util.List;

/**
 * 认定店付款信息DAO.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:20
 */
public interface ShopPayDao {

    /**
     * 添加/更新
     *
     * @param shopPays 认定店集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... shopPays);

    /**
     * 查询认定店付款信息
     *
     * @return 付款信息
     */
    public List<ShopPay> query();

    /**
     * 删除
     *
     * @param shopPays 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... shopPays);
}
