package kaitou.ppp.domain.count;

import kaitou.ppp.domain.BaseDomain;

/**
 * 按认定店统计.
 * User: 赵立伟
 * Date: 2015/2/1
 * Time: 14:48
 */
public class CountByShop extends BaseDomain {

    private String shopId;
    private String shopName;
    private String productLine;
    private int count = 0;

    @Override
    public void check() {

    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountByShop that = (CountByShop) o;

        if (productLine != null ? !productLine.equals(that.productLine) : that.productLine != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shopId != null ? shopId.hashCode() : 0;
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CountByShop{" +
                "shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", productLine='" + productLine + '\'' +
                ", count=" + count +
                '}';
    }

    public int getCount() {
        return count;
    }

    public CountByShop setCount(int count) {
        this.count = count;
        return this;
    }

    public String getShopId() {
        return shopId;
    }

    public CountByShop setShopId(String shopId) {
        this.shopId = shopId;
        return this;
    }

    public String getShopName() {
        return shopName;
    }

    public CountByShop setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public String getProductLine() {
        return productLine;
    }

    public CountByShop setProductLine(String productLine) {
        this.productLine = productLine;
        return this;
    }
}
