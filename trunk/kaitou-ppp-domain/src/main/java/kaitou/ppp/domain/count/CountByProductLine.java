package kaitou.ppp.domain.count;

import kaitou.ppp.domain.BaseDomain;

/**
 * 按产品线统计.
 * User: 赵立伟
 * Date: 2015/2/1
 * Time: 14:48
 */
public class CountByProductLine extends BaseDomain {

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

        CountByProductLine that = (CountByProductLine) o;

        return !(productLine != null ? !productLine.equals(that.productLine) : that.productLine != null);

    }

    @Override
    public int hashCode() {
        return productLine != null ? productLine.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CountByProductLine{" +
                "productLine='" + productLine + '\'' +
                ", count=" + count +
                '}';
    }

    public String getProductLine() {
        return productLine;
    }

    public CountByProductLine setProductLine(String productLine) {
        this.productLine = productLine;
        return this;
    }

    public int getCount() {
        return count;
    }

    public CountByProductLine setCount(int count) {
        this.count = count;
        return this;
    }
}
