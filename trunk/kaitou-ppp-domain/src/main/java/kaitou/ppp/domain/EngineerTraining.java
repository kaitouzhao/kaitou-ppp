package kaitou.ppp.domain;

import org.apache.commons.lang.StringUtils;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;

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
     */
    protected String saleRegion;
    /**
     * 认定点名
     */
    protected String companyName;
    /**
     * 认定点等级
     */
    protected String companyLevel;
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

    /**
     * 构建工程师对象
     *
     * @return 工程师对象
     */
    public Engineer build() {
        Engineer engineer = new Engineer();
        copyBean(this, engineer);
        engineer.addTraining(this);
        return engineer;
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
        if (StringUtils.isEmpty(companyName)) {
            throw new RuntimeException("认定点名为空");
        }
        if (StringUtils.isEmpty(productLine)) {
            throw new RuntimeException("产品线为空");
        }
    }

    @Override
    public String toString() {
        return "EngineerTraining{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyLevel='" + companyLevel + '\'' +
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
        return saleRegion;
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
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
