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

        if (dateOfTraining != null ? !dateOfTraining.equals(training.getDateOfTraining()) : training.getDateOfTraining() != null)
            return false;
        if (id != null ? !id.equals(training.getId()) : training.getId() != null) return false;
        if (productLine != null ? !productLine.equals(training.getProductLine()) : training.getProductLine() != null)
            return false;
        if (trainer != null ? !trainer.equals(training.getTrainer()) : training.getTrainer() != null) return false;
        if (trainingModel != null ? !trainingModel.equals(training.getTrainingModel()) : training.getTrainingModel() != null)
            return false;
        if (trainingType != null ? !trainingType.equals(training.getTrainingType()) : training.getTrainingType() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (trainer != null ? trainer.hashCode() : 0);
        result = 31 * result + (trainingType != null ? trainingType.hashCode() : 0);
        result = 31 * result + (dateOfTraining != null ? dateOfTraining.hashCode() : 0);
        result = 31 * result + (trainingModel != null ? trainingModel.hashCode() : 0);
        return result;
    }

    @Override
    public String dbFileName() {
        StringBuilder dbFileName = new StringBuilder();
        dbFileName.append(saleRegion).append('_').append(shopId).append('_').append("EngineerTraining.kdb");
        return dbFileName.toString();
    }
}
