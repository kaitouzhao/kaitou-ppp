package kaitou.ppp.manager.mock.manager;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.manager.shop.impl.ShopDetailManagerImpl;

import java.util.List;

/**
 * ShopDetailManagerImpl桩.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 18:24
 */
public class MockShopDetailManagerImpl extends ShopDetailManagerImpl {

    @Override
    public int save(List<ShopDetail> shopDetails) {
        ShopDetail[] shopDetailsArray = CollectionUtil.toArray(shopDetails, ShopDetail.class);
        return shopDetailDao.save(shopDetailsArray);
    }
}
