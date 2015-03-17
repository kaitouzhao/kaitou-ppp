package kaitou.ppp.manager.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopRTS;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.shop.ShopRTSManager;

import java.util.List;

/**
 * 认定店RTS信息DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:31
 */
public class ShopRTSManagerImpl extends BaseFileDaoManager<ShopRTS> implements ShopRTSManager, ShopUpdateListener {

    @Override
    public Class<ShopRTS> domainClass() {
        return ShopRTS.class;
    }

    @Override
    public String getEntityName() {
        return domainClass().getSimpleName();
    }

    @Override
    public List<ShopRTS> query() {
        return super.query();
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        if (CollectionUtil.isEmpty(shops)) {
            return;
        }
        List<ShopRTS> shopRTSes = query();
        for (ShopRTS rts : shopRTSes) {
            for (Shop shop : shops) {
                if (!shop.getId().equals(rts.getId())) {
                    continue;
                }
                rts.setName(shop.getName());
            }
        }
        save(shopRTSes);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {
        // DO NOTHING
    }
}
