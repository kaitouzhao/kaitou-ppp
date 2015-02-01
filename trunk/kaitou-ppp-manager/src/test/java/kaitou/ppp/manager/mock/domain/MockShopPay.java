package kaitou.ppp.manager.mock.domain;

import kaitou.ppp.domain.shop.ShopPay;

/**
 * ShopPay桩.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:49
 */
public class MockShopPay extends ShopPay {
    public MockShopPay(String id, String name, String payCode, String payName, String accountBank, String accountNo) {
        this.id = id;
        this.name = name;
        this.payCode = payCode;
        this.payName = payName;
        this.accountBank = accountBank;
        this.accountNo = accountNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        ShopPay shopPay = (ShopPay) o;

        if (id != null ? !id.equals(shopPay.getId()) : shopPay.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String dbFileSuffix() {
        return ShopPay.class.getSimpleName() + ".kdb";
    }
}
