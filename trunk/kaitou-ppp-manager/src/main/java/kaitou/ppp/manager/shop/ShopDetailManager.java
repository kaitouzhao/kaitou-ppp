package kaitou.ppp.manager.shop;

import kaitou.ppp.domain.shop.ShopDetail;

import java.util.List;

/**
 * 认定店明细DAO事务管理层.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 18:10
 */
public interface ShopDetailManager {
    /**
     * 获取db文件目录
     *
     * @return 目录
     */
    public String getDbDir();

    /**
     * 导入认定店明细
     *
     * @param shopDetails 认定店明细列表
     * @return 成功执行个数
     */
    public int save(List<ShopDetail> shopDetails);

    /**
     * 获取认定店明细
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return 认定店明细列表
     */
    public List<ShopDetail> query(String... numberOfYear);

    /**
     * 缓存全部认定店
     */
    public void cacheShop();

    /**
     * 删除
     *
     * @param shopDetails 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... shopDetails);
}
