package kaitou.ppp.manager.shop;

import kaitou.ppp.domain.shop.ShopPay;

import java.util.List;

/**
 * 认定店付款信息DAO事务管理层.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:29
 */
public interface ShopPayManager {
    /**
     * 获取db文件目录
     *
     * @return 目录
     */
    public String getDbDir();

    /**
     * 导入认定店付款信息
     *
     * @param shopPays 认定店付款信息列表
     * @return 成功执行个数
     */
    public int save(List<ShopPay> shopPays);

    /**
     * 获取全部认定店付款信息
     *
     * @return 认定店付款信息列表
     */
    public List<ShopPay> query();

    /**
     * 删除
     *
     * @param shopPays 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... shopPays);
}
