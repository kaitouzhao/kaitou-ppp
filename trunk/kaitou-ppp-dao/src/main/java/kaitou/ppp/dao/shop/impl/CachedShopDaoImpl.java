package kaitou.ppp.dao.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.shop.CachedShopDao;
import kaitou.ppp.domain.shop.CachedShop;
import kaitou.ppp.domain.shop.CachedShopDetail;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;
import static com.womai.bsp.tool.utils.JsonUtil.json2Object;
import static com.womai.bsp.tool.utils.JsonUtil.object2Json;
import static kaitou.ppp.dao.cache.CacheManager.cachedShopMap;

/**
 * 认定店缓存DAO实现.
 * User: 赵立伟
 * Date: 2015/1/28
 * Time: 16:33
 */
public class CachedShopDaoImpl implements CachedShopDao {

    @Override
    public void updateShops(Object... shops) {
        if (CollectionUtil.isEmpty(shops)) {
            return;
        }
        Shop shop;
        CachedShop cachedShop;
        String id;
        for (int i = 0; i < shops.length; i++) {
            shop = (Shop) shops[i];
            id = shop.getId();
            if (StringUtils.isEmpty(id)) {
                continue;
            }
            if (!cachedShopMap.containsKey(id)) {
                cachedShop = new CachedShop();
            } else {
                cachedShop = json2Object(cachedShopMap.get(id), CachedShop.class);
            }
            copyBean(shop, cachedShop);
            cachedShopMap.put(id, object2Json(cachedShop));
        }
    }

    @Override
    public void updateShopDetails(Object... shopDetails) {
        if (CollectionUtil.isEmpty(shopDetails)) {
            return;
        }
        ShopDetail shopDetail;
        CachedShop cachedShop;
        CachedShopDetail cachedShopDetail;
        String id;
        for (int i = 0; i < shopDetails.length; i++) {
            shopDetail = (ShopDetail) shopDetails[i];
            id = shopDetail.getId();
            if (StringUtils.isEmpty(id)) {
                continue;
            }
            if (!cachedShopMap.containsKey(id)) {
                cachedShop = new CachedShop();
                copyBean(shopDetail, cachedShop);
            } else {
                cachedShop = json2Object(cachedShopMap.get(id), CachedShop.class);
            }
            cachedShopDetail = new CachedShopDetail();
            copyBean(shopDetail, cachedShopDetail);
            cachedShop.updateDetail(cachedShopDetail);
            cachedShopMap.put(id, object2Json(cachedShop));
        }
    }

    @Override
    public CachedShopDetail getCachedShopDetail(String shopId, String productLine) {
        List<CachedShopDetail> details = queryCachedShopDetails(shopId);
        int index = details.indexOf(new CachedShopDetail().setProductLine(productLine));
        return index < 0 ? new CachedShopDetail() : details.get(index);
    }

    @Override
    public List<CachedShopDetail> queryCachedShopDetails(String shopId) {
        if (CollectionUtil.isEmpty(cachedShopMap) || StringUtils.isEmpty(shopId)) {
            return new ArrayList<CachedShopDetail>();
        }
        String jsonResult = cachedShopMap.get(shopId);
        if (StringUtils.isEmpty(jsonResult)) {
            return new ArrayList<CachedShopDetail>();
        }
        CachedShop cachedShop = json2Object(jsonResult, CachedShop.class);
        return cachedShop.getDetails();
    }
}
