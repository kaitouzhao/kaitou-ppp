package kaitou.ppp.dao.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.shop.ShopDao;
import kaitou.ppp.domain.shop.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * 认定店DAO实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:14
 */
public class ShopDaoImpl extends BaseDao<Shop> implements ShopDao {
    @Override
    public Class<Shop> getDomainClass() {
        return Shop.class;
    }

    @Override
    public List<Shop> query() {
        return super.query();
    }

    @Override
    public void preSave(Object... domains) {
        List<String> shopIds = new ArrayList<String>();
        List<Integer> indexList = new ArrayList<Integer>();
        Shop shop;
        for (int i = 0; i < domains.length; i++) {
            shop = (Shop) domains[i];
            String shopId = shop.getId();
            if (shopIds.contains(shopId)) {
                indexList.add(i + 2);
                continue;
            }
            shopIds.add(shopId);
        }
        if (!CollectionUtil.isEmpty(indexList)) {
            StringBuilder info = new StringBuilder();
            info.append("认定店编号重复。行数：");
            for (Integer index : indexList) {
                info.append(index).append(" ");
            }
            logOperation(info.toString());
            throw new RuntimeException(info.toString());
        }
    }
}
