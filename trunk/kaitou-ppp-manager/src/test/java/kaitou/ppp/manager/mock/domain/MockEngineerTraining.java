package kaitou.ppp.manager.mock.domain;

import kaitou.ppp.domain.engineer.EngineerTraining;

/**
 * 工程师培训信息桩.
 * User: 赵立伟
 * Date: 2015/1/18
 * Time: 17:32
 */
public class MockEngineerTraining extends EngineerTraining {

    public MockEngineerTraining(String id, String name, String saleRegion, String shopName, String shopLevel, String numberOfYear, String ACELevel, String dateOfEntry, String dateOfDeparture, String status, String productLine, String trainer, String trainingType, String dateOfTraining, String trainingModel, String shopId) {
        this.id = id;
        this.name = name;
        this.saleRegion = saleRegion;
        this.shopName = shopName;
        this.shopLevel = shopLevel;
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
        this.shopId = shopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        EngineerTraining training = (EngineerTraining) o;

        if (!aceLevel.equals(training.getAceLevel())) return false;
        if (!dateOfDeparture.equals(training.getDateOfDeparture())) return false;
        if (!dateOfEntry.equals(training.getDateOfEntry())) return false;
        if (!dateOfTraining.equals(training.getDateOfTraining())) return false;
        if (!id.equals(training.getId())) return false;
        if (!name.equals(training.getName())) return false;
        if (!numberOfYear.equals(training.getNumberOfYear())) return false;
        if (!productLine.equals(training.getProductLine())) return false;
        if (!saleRegion.equals(training.getSaleRegion())) return false;
        if (!shopId.equals(training.getShopId())) return false;
        if (!shopLevel.equals(training.getShopLevel())) return false;
        if (!shopName.equals(training.getShopName())) return false;
        if (!status.equals(training.getStatus())) return false;
        if (!trainer.equals(training.getTrainer())) return false;
        if (!trainingModel.equals(training.getTrainingModel())) return false;
        if (!trainingType.equals(training.getTrainingType())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + saleRegion.hashCode();
        result = 31 * result + shopId.hashCode();
        result = 31 * result + shopName.hashCode();
        result = 31 * result + shopLevel.hashCode();
        result = 31 * result + numberOfYear.hashCode();
        result = 31 * result + aceLevel.hashCode();
        result = 31 * result + dateOfEntry.hashCode();
        result = 31 * result + dateOfDeparture.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + productLine.hashCode();
        result = 31 * result + trainer.hashCode();
        result = 31 * result + trainingType.hashCode();
        result = 31 * result + dateOfTraining.hashCode();
        result = 31 * result + trainingModel.hashCode();
        return result;
    }

    @Override
    public String dbFileName() {
        StringBuilder dbFileName = new StringBuilder();
        dbFileName.append(saleRegion).append('_').append(shopId).append('_').append("EngineerTraining.kdb");
        return dbFileName.toString();
    }
}
