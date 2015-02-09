package kaitou.ppp.domain.engineer;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 工程师培训信息.
 * User: 赵立伟
 * Date: 2015/1/18
 * Time: 16:44
 */
public class EngineerTraining extends BaseDomain {
    /**
     * 编号
     */
    protected String id;
    /**
     * 姓名
     */
    protected String name;
    /**
     * 销售区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    protected String saleRegion;
    /**
     * 认定店编码
     */
    protected String shopId;
    /**
     * 认定店名
     */
    protected String shopName;
    /**
     * 认定店等级
     */
    protected String shopLevel;
    /**
     * 认定年限
     */
    protected String numberOfYear;
    /**
     * ACE等级
     */
    protected String aceLevel;
    /**
     * 入职日期
     */
    protected String dateOfEntry;
    /**
     * 离职日期
     */
    protected String dateOfDeparture;
    /**
     * 状态
     */
    protected String status;
    /**
     * 产品线
     */
    protected String productLine;
    /**
     * 培训师
     */
    protected String trainer;
    /**
     * 培训类型
     */
    protected String trainingType;
    /**
     * 培训日期
     */
    protected String dateOfTraining;
    /**
     * 培训机型
     */
    protected String trainingModel;

    @Override
    public String dbFileSuffix() {
        return '_' + shopId + '_' + getClass().getSimpleName() + ".kdb";
    }

    @Override
    public String dbFileName() {
        StringBuilder dbFileName = new StringBuilder();
        dbFileName.append(SysCode.SaleRegion.convert2Code(saleRegion)).append(dbFileSuffix());
        return dbFileName.toString();
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("编号为空");
        }
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("姓名为空");
        }
        if (StringUtils.isEmpty(saleRegion)) {
            throw new RuntimeException("区域为空");
        }
        if (StringUtils.isEmpty(shopId)) {
            throw new RuntimeException("认定店编码为空");
        }
        if (StringUtils.isEmpty(shopName)) {
            throw new RuntimeException("认定店名为空");
        }
        if (StringUtils.isEmpty(productLine)) {
            throw new RuntimeException("产品线为空");
        }
        if (StringUtils.isEmpty(trainer)) {
            throw new RuntimeException("培训师为空");
        }
        if (StringUtils.isEmpty(trainingModel)) {
            throw new RuntimeException("培训机型为空");
        }
        if (StringUtils.isEmpty(trainingType)) {
            throw new RuntimeException("培训类型为空");
        }
        if (StringUtils.isEmpty(dateOfTraining)) {
            throw new RuntimeException("培训时间为空");
        }
    }

    @Override
    public String toString() {
        return "EngineerTraining{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopLevel='" + shopLevel + '\'' +
                ", numberOfYear='" + numberOfYear + '\'' +
                ", aceLevel='" + aceLevel + '\'' +
                ", dateOfEntry='" + dateOfEntry + '\'' +
                ", dateOfDeparture='" + dateOfDeparture + '\'' +
                ", status='" + status + '\'' +
                ", productLine='" + productLine + '\'' +
                ", trainer='" + trainer + '\'' +
                ", trainingType='" + trainingType + '\'' +
                ", dateOfTraining='" + dateOfTraining + '\'' +
                ", trainingModel='" + trainingModel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EngineerTraining training = (EngineerTraining) o;

        if (!dateOfTraining.equals(training.dateOfTraining)) return false;
        if (!id.equals(training.id)) return false;
        if (!productLine.equals(training.productLine)) return false;
        if (!saleRegion.equals(training.saleRegion)) return false;
        if (!shopId.equals(training.shopId)) return false;
        if (!trainer.equals(training.trainer)) return false;
        if (!trainingModel.equals(training.trainingModel)) return false;
        if (!trainingType.equals(training.trainingType)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + saleRegion.hashCode();
        result = 31 * result + shopId.hashCode();
        result = 31 * result + productLine.hashCode();
        result = 31 * result + trainer.hashCode();
        result = 31 * result + trainingType.hashCode();
        result = 31 * result + dateOfTraining.hashCode();
        result = 31 * result + trainingModel.hashCode();
        return result;
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

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
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

    public String getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getAceLevel() {
        return aceLevel;
    }

    public void setAceLevel(String aceLevel) {
        this.aceLevel = aceLevel;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getDateOfTraining() {
        return dateOfTraining;
    }

    public void setDateOfTraining(String dateOfTraining) {
        this.dateOfTraining = dateOfTraining;
    }

    public String getTrainingModel() {
        return trainingModel;
    }

    public void setTrainingModel(String trainingModel) {
        this.trainingModel = trainingModel;
    }
}
