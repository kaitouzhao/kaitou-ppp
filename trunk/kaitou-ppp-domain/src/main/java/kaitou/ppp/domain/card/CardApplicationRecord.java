package kaitou.ppp.domain.card;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

/**
 * 保修卡生成记录.
 * User: 赵立伟
 * Date: 2015/3/7
 * Time: 21:22
 */
public class CardApplicationRecord extends BaseDomain {
    private String applyDate;
    private String status;
    private String isBack;
    private String warrantyCard;
    private String allModels;
    private String models;
    private String fuselage;
    private String shopId;
    private String shopName;
    private String installedDate;
    private String endDate;
    private String initData;
    private String userCompanyName;
    private String userLinkMan;
    private String userContact;
    private String installedAddress;

    @Override
    public void check() {
        if (StringUtils.isEmpty(warrantyCard)) {
            throw new RuntimeException("保修卡号为空");
        }
        if (StringUtils.isEmpty(shopId)) {
            throw new RuntimeException("认定店编码为空");
        }
        if (StringUtils.isEmpty(shopName)) {
            throw new RuntimeException("认定店名称为空");
        }
    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return getClass().getSimpleName() + ".kdb";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardApplicationRecord that = (CardApplicationRecord) o;

        if (warrantyCard != null ? !warrantyCard.equals(that.warrantyCard) : that.warrantyCard != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return warrantyCard != null ? warrantyCard.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CardApplicationRecord{" +
                "applyDate='" + applyDate + '\'' +
                ", status='" + status + '\'' +
                ", isBack='" + isBack + '\'' +
                ", warrantyCard='" + warrantyCard + '\'' +
                ", allModels='" + allModels + '\'' +
                ", models='" + models + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", installedDate='" + installedDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", initData='" + initData + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", userLinkMan='" + userLinkMan + '\'' +
                ", userContact='" + userContact + '\'' +
                ", installedAddress='" + installedAddress + '\'' +
                '}';
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAllModels() {
        return allModels;
    }

    public void setAllModels(String allModels) {
        this.allModels = allModels;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsBack() {
        return isBack;
    }

    public void setIsBack(String isBack) {
        this.isBack = isBack;
    }

    public String getWarrantyCard() {
        return warrantyCard;
    }

    public void setWarrantyCard(String warrantyCard) {
        this.warrantyCard = warrantyCard;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getFuselage() {
        return fuselage;
    }

    public void setFuselage(String fuselage) {
        this.fuselage = fuselage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getInstalledDate() {
        return installedDate;
    }

    public void setInstalledDate(String installedDate) {
        this.installedDate = installedDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInitData() {
        return initData;
    }

    public void setInitData(String initData) {
        this.initData = initData;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserLinkMan() {
        return userLinkMan;
    }

    public void setUserLinkMan(String userLinkMan) {
        this.userLinkMan = userLinkMan;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getInstalledAddress() {
        return installedAddress;
    }

    public void setInstalledAddress(String installedAddress) {
        this.installedAddress = installedAddress;
    }
}
