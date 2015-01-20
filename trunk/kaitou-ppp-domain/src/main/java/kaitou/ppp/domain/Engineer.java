package kaitou.ppp.domain;

import com.womai.bsp.tool.utils.CollectionUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 工程师.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 10:36
 */
public class Engineer extends BaseDomain {
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
     * 培训信息
     */
    private List<EngineerTraining> engineerTrainings = new ArrayList<EngineerTraining>();

    /**
     * 加入培训信息
     *
     * @param training 培训信息
     * @return 培训信息列表
     */
    public List<EngineerTraining> addTraining(EngineerTraining training) {
        engineerTrainings.add(training);
        return engineerTrainings;
    }

    /**
     * 生成数据文件名
     *
     * @return 文件名
     */
    public String dbFileName() {
        StringBuilder dbFileName = new StringBuilder();
        dbFileName.append(saleRegion).append('_').append(companyName).append('_').append(getClass().getSimpleName() + ".kdb");
        return dbFileName.toString();
    }

    /**
     * 生成数据文件备份名
     *
     * @return 备份名
     */
    public String backDbFileName() {
        StringBuilder backDbFileName = new StringBuilder(dbFileName());
        backDbFileName.append(".back");
        return backDbFileName.toString();
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
    }

    @Override
    public String toString() {
        return "Engineer{" +
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
                ", engineerTrainings=" + engineerTrainings +
                '}';
    }

    public List<EngineerTraining> getEngineerTrainings() {
        return engineerTrainings;
    }

    public void setEngineerTrainings(List<EngineerTraining> engineerTrainings) {
        this.engineerTrainings = engineerTrainings;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSaleRegion() {
        return saleRegion;
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
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
}
