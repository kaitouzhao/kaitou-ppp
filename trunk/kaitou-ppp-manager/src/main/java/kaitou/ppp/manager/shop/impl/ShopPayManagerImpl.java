package kaitou.ppp.manager.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.shop.ShopPayManager;

import java.util.List;

/**
 * 认定店付款信息DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:34
 */
public class ShopPayManagerImpl extends BaseFileDaoManager<ShopPay> implements ShopPayManager, ShopUpdateListener {

    @Override
    public Class<ShopPay> domainClass() {
        return ShopPay.class;
    }

    @Override
    public String getEntityName() {
        return domainClass().getSimpleName();
    }

    @Override
    public List<ShopPay> query() {
        return super.query();
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        if (CollectionUtil.isEmpty(shops)) {
            return;
        }
        List<ShopPay> shopPays = query();
        for (ShopPay pay : shopPays) {
            for (Shop shop : shops) {
                if (!shop.getId().equals(pay.getId())) {
                    continue;
                }
                pay.setName(shop.getName());
            }
        }
        save(shopPays);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {
        // DO NOTHING
    }
}
