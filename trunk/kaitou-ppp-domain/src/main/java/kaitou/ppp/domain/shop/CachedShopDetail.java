package kaitou.ppp.domain.shop;

/**
 * 缓存的认定店发展信息.
 * User: 赵立伟
 * Date: 2015/1/28
 * Time: 13:06
 */
public class CachedShopDetail {
    /**
     * 产品线
     */
    private String productLine;
    /**
     * 认定年份
     */
    private String numberOfYear;
    /**
     * 认定级别
     */
    private String level;
    /**
     * 机型
     */
    private String model;

    /**
     * 校验是否晚于当前
     *
     * @param o 待校验信息
     * @return 晚则为真
     */
    public boolean isBefore(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CachedShopDetail detail = (CachedShopDetail) o;
        try {
            return Long.valueOf(numberOfYear).compareTo(Long.valueOf(detail.numberOfYear)) < 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CachedShopDetail detail = (CachedShopDetail) o;

        if (productLine != null ? !productLine.equals(detail.productLine) : detail.productLine != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return productLine != null ? productLine.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CachedShopDetail{" +
                "productLine='" + productLine + '\'' +
                ", numberOfYear='" + numberOfYear + '\'' +
                ", level='" + level + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    public String getModel() {
        return model;
    }

    public CachedShopDetail setModel(String model) {
        this.model = model;
        return this;
    }

    public String getProductLine() {
        return productLine;
    }

    public CachedShopDetail setProductLine(String productLine) {
        this.productLine = productLine;
        return this;
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public CachedShopDetail setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public CachedShopDetail setLevel(String level) {
        this.level = level;
        return this;
    }
}
