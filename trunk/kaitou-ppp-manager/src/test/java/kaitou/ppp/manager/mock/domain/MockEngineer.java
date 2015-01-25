package kaitou.ppp.manager.mock.domain;

import kaitou.ppp.domain.engineer.Engineer;

/**
 * 工程师桩.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 13:32
 */
public class MockEngineer extends Engineer {

    public MockEngineer(String id, String name, String saleRegion, String shopName, String shopLevel, String numberOfYear, String ACELevel, String dateOfEntry, String dateOfDeparture, String status, String shopId, String productLine, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.saleRegion = saleRegion;
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopLevel = shopLevel;
        this.numberOfYear = numberOfYear;
        this.aceLevel = ACELevel;
        this.dateOfEntry = dateOfEntry;
        this.dateOfDeparture = dateOfDeparture;
        this.status = status;
        this.productLine = productLine;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Engineer engineer = (Engineer) o;

        if (!id.equals(engineer.getId())) return false;
        if (!productLine.equals(engineer.getProductLine())) return false;
        if (!saleRegion.equals(engineer.getSaleRegion())) return false;
        if (!shopId.equals(engineer.getShopId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = saleRegion.hashCode();
        result = 31 * result + shopId.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + productLine.hashCode();
        return result;
    }

    @Override
    public String dbFileSuffix() {
        return '_' + shopId + '_' + "Engineer.kdb";
    }
}
