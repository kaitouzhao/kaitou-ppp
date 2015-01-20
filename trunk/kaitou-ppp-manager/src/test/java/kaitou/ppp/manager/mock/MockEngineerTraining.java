package kaitou.ppp.manager.mock;

import kaitou.ppp.domain.Engineer;
import kaitou.ppp.domain.EngineerTraining;

/**
 * 工程师培训信息桩.
 * User: 赵立伟
 * Date: 2015/1/18
 * Time: 17:32
 */
public class MockEngineerTraining extends EngineerTraining {

    public MockEngineerTraining(String id, String name, String saleRegion, String companyName, String companyLevel, String numberOfYear, String ACELevel, String dateOfEntry, String dateOfDeparture, String status, String productLine, String trainer, String trainingType, String dateOfTraining, String trainingModel) {
        this.id = id;
        this.name = name;
        this.saleRegion = saleRegion;
        this.companyName = companyName;
        this.companyLevel = companyLevel;
        this.numberOfYear = numberOfYear;
        this.aceLevel = ACELevel;
        this.dateOfEntry = dateOfEntry;
        this.dateOfDeparture = dateOfDeparture;
        this.status = status;
        this.productLine = productLine;
        this.trainer = trainer;
        this.trainingType = trainingType;
        this.dateOfTraining = dateOfTraining;
        this.trainingModel = trainingModel;
    }

    @Override
    public Engineer build() {
        MockEngineer engineer = new MockEngineer(id, name, saleRegion, companyName, companyLevel, numberOfYear, aceLevel, dateOfEntry, dateOfDeparture, status);
        engineer.addTraining(this);
        return engineer;
    }
}
