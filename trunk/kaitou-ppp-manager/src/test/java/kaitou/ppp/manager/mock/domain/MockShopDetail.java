package kaitou.ppp.manager.mock.domain;

import kaitou.ppp.domain.shop.ShopDetail;

/**
 * ShopDetail桩.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 18:17
 */
public class MockShopDetail extends ShopDetail {

    public MockShopDetail(String saleRegion, String id, String name, String numberOfYear, String productLine, String level, String model) {
        this.saleRegion = saleRegion;
        this.id = id;
        this.name = name;
        this.numberOfYear = numberOfYear;
        this.productLine = productLine;
        this.level = level;
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        ShopDetail that = (ShopDetail) o;

        if (id != null ? !id.equals(that.getId()) : that.getId() != null) return false;
        if (model != null ? !model.equals(that.getModel()) : that.getModel() != null) return false;
        if (numberOfYear != null ? !numberOfYear.equals(that.getNumberOfYear()) : that.getNumberOfYear() != null)
            return false;
        if (productLine != null ? !productLine.equals(that.getProductLine()) : that.getProductLine() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (numberOfYear != null ? numberOfYear.hashCode() : 0);
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }

    @Override
    public String dbFileSuffix() {
        return '_' + numberOfYear + '_' + "ShopDetail.kdb";
    }
}
