package kaitou.ppp.manager.mock.domain;

import kaitou.ppp.domain.shop.ShopRTS;

/**
 * ShopRTS桩.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:42
 */
public class MockShopRTS extends ShopRTS {
    public MockShopRTS(String id, String name, String productLine, String rts) {
        this.id = id;
        this.name = name;
        this.productLine = productLine;
        this.rts = rts;
    }

    @Override
    public String dbFileSuffix() {
        return ShopRTS.class.getSimpleName() + ".kdb";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        ShopRTS shopRTS = (ShopRTS) o;

        if (id != null ? !id.equals(shopRTS.getId()) : shopRTS.getId() != null) return false;
        if (productLine != null ? !productLine.equals(shopRTS.getProductLine()) : shopRTS.getProductLine() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
