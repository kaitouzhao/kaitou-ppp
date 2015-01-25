package kaitou.ppp.domain.shop;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

/**
 * 认定店明细.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 17:47
 */
public class ShopDetail extends BaseDomain {
    /**
     * 销售区域
     */
    protected String saleRegion;
    /**
     * 认定店编号
     */
    protected String id;
    /**
     * 认定店名
     */
    protected String name;
    /**
     * 认定年份
     */
    protected String numberOfYear;
    /**
     * 产品线
     */
    protected String productLine;
    /**
     * 认定级别
     */
    protected String level;
    /**
     * 认定机型
     */
    protected String model;

    @Override
    public void check() {
        if (StringUtils.isEmpty(saleRegion)) {
            throw new RuntimeException("区域为空");
        }
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("认定店编号为空");
        }
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("认定店名为空");
        }
        if (StringUtils.isEmpty(numberOfYear)) {
            throw new RuntimeException("认定年份为空");
        }
        if (StringUtils.isEmpty(productLine)) {
            throw new RuntimeException("产品线为空");
        }
        if (StringUtils.isEmpty(level)) {
            throw new RuntimeException("认定级别为空");
        }
        if (StringUtils.isEmpty(model)) {
            throw new RuntimeException("认定机型为空");
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
        return '_' + numberOfYear + '_' + getClass().getSimpleName() + ".kdb";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopDetail that = (ShopDetail) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (numberOfYear != null ? !numberOfYear.equals(that.numberOfYear) : that.numberOfYear != null) return false;
        if (productLine != null ? !productLine.equals(that.productLine) : that.productLine != null) return false;
        if (saleRegion != null ? !saleRegion.equals(that.saleRegion) : that.saleRegion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = saleRegion != null ? saleRegion.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (numberOfYear != null ? numberOfYear.hashCode() : 0);
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShopDetail{" +
                "saleRegion='" + saleRegion + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", numberOfYear='" + numberOfYear + '\'' +
                ", productLine='" + productLine + '\'' +
                ", level='" + level + '\'' +
                ", model='" + model + '\'' +
                '}';
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

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}