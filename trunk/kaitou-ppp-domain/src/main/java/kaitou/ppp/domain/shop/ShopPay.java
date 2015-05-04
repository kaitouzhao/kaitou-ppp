package kaitou.ppp.domain.shop;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

/**
 * 认定店付款信息.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:10
 */
public class ShopPay extends BaseDomain {
    /**
     * 认定店编码
     */
    protected String id;
    /**
     * 认定店名
     */
    protected String name;
    /**
     * 付款代码
     */
    protected String payCode;
    /**
     * 付款名称
     */
    protected String payName;
    /**
     * 开户行
     */
    protected String accountBank;
    /**
     * 帐号
     */
    protected String accountNo;

    @Override
    public void check() {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("认定店编码为空");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopPay shopPay = (ShopPay) o;

        return !(id != null ? !id.equals(shopPay.id) : shopPay.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ShopPay{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", payCode='" + payCode + '\'' +
                ", payName='" + payName + '\'' +
                ", accountBank='" + accountBank + '\'' +
                ", accountNo='" + accountNo + '\'' +
                '}';
    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return getClass().getSimpleName() + DB_SUFFIX;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
