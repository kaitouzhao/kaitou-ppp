package kaitou.ppp.manager.listener;

import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;

/**
 * 认定店更新监听对象接口.
 * User: 赵立伟
 * Date: 2015/2/9
 * Time: 10:14
 */
public interface ShopUpdateListener {
    /**
     * 更新认定店基本信息事件
     *
     * @param shops 认定店集合
     */
    public void updateShopEvent(Shop... shops);

    /**
     * 更新认定店认定级别
     *
     * @param shopDetails 认定级别集合
     */
    public void updateShopDetailEvent(ShopDetail... shopDetails);
}
