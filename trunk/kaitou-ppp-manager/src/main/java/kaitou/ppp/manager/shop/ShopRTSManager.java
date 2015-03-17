package kaitou.ppp.manager.shop;

import kaitou.ppp.domain.shop.ShopRTS;

import java.util.List;

/**
 * 认定店RTS信息DAO事务管理层.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:28
 */
public interface ShopRTSManager {
    /**
     * 获取db文件目录
     *
     * @return 目录
     */
    public String getDbDir();

    /**
     * 导入认定店RTS
     *
     * @param shopRTSs 认定店RTS列表
     * @return 成功执行个数
     */
    public int save(List<ShopRTS> shopRTSs);

    /**
     * 获取全部认定店RTS
     *
     * @return 认定店RTS列表
     */
    public List<ShopRTS> query();

    /**
     * 删除
     *
     * @param shopRTSs 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... shopRTSs);
}
