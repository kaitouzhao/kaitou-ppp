package kaitou.ppp.manager.mock.domain;

import kaitou.ppp.domain.shop.Shop;

/**
 * Shop桩.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 12:32
 */
public class MockShop extends Shop {
    public MockShop() {
    }

    public MockShop(String saleRegion, String id, String name, String linkMan, String phone, String address, String email) {
        this.saleRegion = saleRegion;
        this.id = id;
        this.name = name;
        this.linkMan = linkMan;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Shop shop = (Shop) o;

        if (id != null ? !id.equals(shop.getId()) : shop.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String dbFileSuffix() {
        return '_' + "Shop.kdb";
    }
}
