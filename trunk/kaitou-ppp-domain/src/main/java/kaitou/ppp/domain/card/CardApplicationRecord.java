package kaitou.ppp.domain.card;

import kaitou.ppp.domain.BaseDomain4InDoubt;
import kaitou.ppp.domain.annotation.PKField;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 保修卡生成记录.
 * User: 赵立伟
 * Date: 2015/3/7
 * Time: 21:22
 */
public class CardApplicationRecord extends BaseDomain4InDoubt {
    private String applyDate;
    private String status;
    private String isBack;
    private String warrantyCard;
    private String allModels;
    private String models;
    @PKField(PKViolationType = SysCode.PKViolationType.IN_DOUBT)
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
        if (StringUtils.isEmpty(fuselage)) {
            doInDoubt();
        }
    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return getClass().getSimpleName() + DB_SUFFIX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardApplicationRecord that = (CardApplicationRecord) o;

        if (serialNo > 0) {
            return super.equals(o);
        }

        if (allModels != null ? !allModels.equals(that.allModels) : that.allModels != null) return false;
        if (applyDate != null ? !applyDate.equals(that.applyDate) : that.applyDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (fuselage != null ? !fuselage.equals(that.fuselage) : that.fuselage != null) return false;
        if (initData != null ? !initData.equals(that.initData) : that.initData != null) return false;
        if (installedAddress != null ? !installedAddress.equals(that.installedAddress) : that.installedAddress != null)
            return false;
        if (installedDate != null ? !installedDate.equals(that.installedDate) : that.installedDate != null)
            return false;
        if (isBack != null ? !isBack.equals(that.isBack) : that.isBack != null) return false;
        if (models != null ? !models.equals(that.models) : that.models != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (shopName != null ? !shopName.equals(that.shopName) : that.shopName != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (userCompanyName != null ? !userCompanyName.equals(that.userCompanyName) : that.userCompanyName != null)
            return false;
        if (userContact != null ? !userContact.equals(that.userContact) : that.userContact != null) return false;
        if (userLinkMan != null ? !userLinkMan.equals(that.userLinkMan) : that.userLinkMan != null) return false;
        if (warrantyCard != null ? !warrantyCard.equals(that.warrantyCard) : that.warrantyCard != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        if (serialNo > 0) {
            return super.hashCode();
        }
        
        int result = applyDate != null ? applyDate.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isBack != null ? isBack.hashCode() : 0);
        result = 31 * result + (warrantyCard != null ? warrantyCard.hashCode() : 0);
        result = 31 * result + (allModels != null ? allModels.hashCode() : 0);
        result = 31 * result + (models != null ? models.hashCode() : 0);
        result = 31 * result + (fuselage != null ? fuselage.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
        result = 31 * result + (installedDate != null ? installedDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (initData != null ? initData.hashCode() : 0);
        result = 31 * result + (userCompanyName != null ? userCompanyName.hashCode() : 0);
        result = 31 * result + (userLinkMan != null ? userLinkMan.hashCode() : 0);
        result = 31 * result + (userContact != null ? userContact.hashCode() : 0);
        result = 31 * result + (installedAddress != null ? installedAddress.hashCode() : 0);
        return result;
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
