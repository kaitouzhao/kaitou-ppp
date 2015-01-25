package kaitou.ppp.domain.shop;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

/**
 * 认定店.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 11:46
 */
public class Shop extends BaseDomain {
    /**
     * 销售区域
     */
    protected String saleRegion;
    /**
     * 认定店编号（合同编号）
     */
    protected String id;
    /**
     * 认定店名
     */
    protected String name;
    /**
     * 合同联系人
     */
    protected String linkMan;
    /**
     * 联系电话
     */
    protected String phone;
    /**
     * 邮寄地址
     */
    protected String address;
    /**
     * 联系邮箱
     */
    protected String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (id != null ? !id.equals(shop.id) : shop.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "saleRegion='" + saleRegion + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("认定店编码为空");
        }
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("认定店名为空");
        }
    }

    @Override
    public String dbFileName() {
        StringBuilder dbFileName = new StringBuilder();
        dbFileName.append(saleRegion).append(dbFileSuffix());
        return dbFileName.toString();
    }

    @Override
    public String dbFileSuffix() {
        return '_' + getClass().getSimpleName() + ".kdb";
    }

    public String getSaleRegion() {
        return saleRegion;
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
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

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
