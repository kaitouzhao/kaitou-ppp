package kaitou.ppp.manager.mock.manager;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.manager.shop.impl.ShopManagerImpl;

import java.util.List;

/**
 * ShopManagerImpl桩.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:38
 */
public class MockShopManagerImpl extends ShopManagerImpl {

    @Override
    public int save(List<Shop> shops) {
        Shop[] shopArray = CollectionUtil.toArray(shops, Shop.class);
        return shopDao.save(shopArray);
    }
}
