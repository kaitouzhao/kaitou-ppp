package kaitou.ppp.domain.export;

/**
 * 认定级别导出模板1.
 * User: 赵立伟
 * Date: 2015/4/17
 * Time: 20:55
 */
public class ExportShopDetailModel1 {
    private String saleRegion;
    private String shopId;
    private String shopName;
    private String cpp2013 = "";
    private String wfp2013 = "";
    private String ipf2013 = "";
    private String cpp2014 = "";
    private String wfp2014 = "";
    private String ipf2014 = "";
    private String cpp2015 = "";
    private String wfp2015 = "";
    private String ipf2015 = "";
    private String dp2015 = "";
    private String pga2015 = "";
    private String tds2015 = "";
    private String dgs2015 = "";
    private String cppModel = "";
    private String wfpModel = "";
    private String ipfModel = "";
    private String dpModel = "";
    private String pgaModel = "";
    private String tdsModel = "";
    private String dgsModel = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportShopDetailModel1 that = (ExportShopDetailModel1) o;

        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return shopId != null ? shopId.hashCode() : 0;
    }

    public String getSaleRegion() {
        return saleRegion;
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
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

    public String getCpp2013() {
        return cpp2013;
    }

    public void setCpp2013(String cpp2013) {
        this.cpp2013 = cpp2013;
    }

    public String getWfp2013() {
        return wfp2013;
    }

    public void setWfp2013(String wfp2013) {
        this.wfp2013 = wfp2013;
    }

    public String getIpf2013() {
        return ipf2013;
    }

    public void setIpf2013(String ipf2013) {
        this.ipf2013 = ipf2013;
    }

    public String getCpp2014() {
        return cpp2014;
    }

    public void setCpp2014(String cpp2014) {
        this.cpp2014 = cpp2014;
    }

    public String getWfp2014() {
        return wfp2014;
    }

    public void setWfp2014(String wfp2014) {
        this.wfp2014 = wfp2014;
    }

    public String getIpf2014() {
        return ipf2014;
    }

    public void setIpf2014(String ipf2014) {
        this.ipf2014 = ipf2014;
    }

    public String getCpp2015() {
        return cpp2015;
    }

    public void setCpp2015(String cpp2015) {
        this.cpp2015 = cpp2015;
    }

    public String getWfp2015() {
        return wfp2015;
    }

    public void setWfp2015(String wfp2015) {
        this.wfp2015 = wfp2015;
    }

    public String getIpf2015() {
        return ipf2015;
    }

    public void setIpf2015(String ipf2015) {
        this.ipf2015 = ipf2015;
    }

    public String getDp2015() {
        return dp2015;
    }

    public void setDp2015(String dp2015) {
        this.dp2015 = dp2015;
    }

    public String getPga2015() {
        return pga2015;
    }

    public void setPga2015(String pga2015) {
        this.pga2015 = pga2015;
    }

    public String getTds2015() {
        return tds2015;
    }

    public void setTds2015(String tds2015) {
        this.tds2015 = tds2015;
    }

    public String getDgs2015() {
        return dgs2015;
    }

    public void setDgs2015(String dgs2015) {
        this.dgs2015 = dgs2015;
    }

    public String getCppModel() {
        return cppModel;
    }

    public void setCppModel(String cppModel) {
        this.cppModel = cppModel;
    }

    public String getWfpModel() {
        return wfpModel;
    }

    public void setWfpModel(String wfpModel) {
        this.wfpModel = wfpModel;
    }

    public String getIpfModel() {
        return ipfModel;
    }

    public void setIpfModel(String ipfModel) {
        this.ipfModel = ipfModel;
    }

    public String getDpModel() {
        return dpModel;
    }

    public void setDpModel(String dpModel) {
        this.dpModel = dpModel;
    }

    public String getPgaModel() {
        return pgaModel;
    }

    public void setPgaModel(String pgaModel) {
        this.pgaModel = pgaModel;
    }

    public String getTdsModel() {
        return tdsModel;
    }

    public void setTdsModel(String tdsModel) {
        this.tdsModel = tdsModel;
    }

    public String getDgsModel() {
        return dgsModel;
    }

    public void setDgsModel(String dgsModel) {
        this.dgsModel = dgsModel;
    }
}
