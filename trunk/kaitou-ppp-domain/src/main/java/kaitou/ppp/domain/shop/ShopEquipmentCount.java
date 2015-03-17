package kaitou.ppp.domain.shop;

import kaitou.ppp.domain.BaseDomain;

/**
 * 认定店设备统计.
 * User: 赵立伟
 * Date: 2015/3/8
 * Time: 13:38
 */
public class ShopEquipmentCount extends BaseDomain {

    private String shopId;
    private String shopName;
    private int dgs;
    private int dp;
    private int pga;
    private int tds;
    private int ipf;

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

        ShopEquipmentCount that = (ShopEquipmentCount) o;

        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return shopId != null ? shopId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ShopEquipmentCount{" +
                "shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", dgs=" + dgs +
                ", dp=" + dp +
                ", pga=" + pga +
                ", tds=" + tds +
                ", ipf=" + ipf +
                '}';
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getDgs() {
        return dgs;
    }

    public void setDgs(int dgs) {
        this.dgs = dgs;
    }

    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public int getPga() {
        return pga;
    }

    public void setPga(int pga) {
        this.pga = pga;
    }

    public int getTds() {
        return tds;
    }

    public void setTds(int tds) {
        this.tds = tds;
    }

    public int getIpf() {
        return ipf;
    }

    public void setIpf(int ipf) {
        this.ipf = ipf;
    }
}
