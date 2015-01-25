package kaitou.ppp.service.impl;

import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.manager.engineer.EngineerManager;
import kaitou.ppp.manager.engineer.EngineerTrainingManager;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.EngineerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.List;

/**
 * 工程师业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 23:16
 */
public class EngineerServiceImpl extends BaseExcelService implements EngineerService {

    private final Log log = LogFactory.getLog(getClass());

    private EngineerManager engineerManager;
    private EngineerTrainingManager engineerTrainingManager;

    private static final String[] EXCEL_ENGINEER_HEADER = new String[]{"区域", "产品线", "在职状态", "认定店编码", "认定店名称", "认定店等级", "认定年限", "工程师编号", "工程师姓名", "ACE等级", "入职时间", "离职时间", "邮箱", "电话", "地址"};
    private static final String[] EXCEL_TRAINING_HEADER = new String[]{"区域", "产品线", "在职状态", "认定店编码", "认定店名称", "认定店等级", "认定年限", "工程师编号", "工程师姓名", "ACE等级", "入职时间", "离职时间", "培训师", "培训类型", "培训时间", "培训机型"};
    private static final String[] ENGINEER_COLUMN = {"saleRegion", "productLine", "status", "shopId", "shopName", "shopLevel", "numberOfYear", "id", "name", "aceLevel", "dateOfEntry", "dateOfDeparture", "email", "phone", "address"};
    private static final String[] TRAINING_COLUMN = {"saleRegion", "productLine", "status", "shopId", "shopName", "shopLevel", "numberOfYear", "id", "name", "aceLevel", "dateOfEntry", "dateOfDeparture", "trainer", "trainingType", "dateOfTraining", "trainingModel"};

    public void setEngineerTrainingManager(EngineerTrainingManager engineerTrainingManager) {
        this.engineerTrainingManager = engineerTrainingManager;
    }

    public void setEngineerManager(EngineerManager engineerManager) {
        this.engineerManager = engineerManager;
    }

    @Override
    public void importEngineers(File srcFile) {
        List<Engineer> engineers = readFromExcel(srcFile, "基础", ENGINEER_COLUMN, Engineer.class);
        log.info("成功导入工程师数：" + engineerManager.importEngineers(engineers));
    }

    @Override
    public void exportEngineers(File targetFile) {
        export2Excel(engineerManager.query(), "基础", EXCEL_ENGINEER_HEADER, ENGINEER_COLUMN, targetFile);
    }

    @Override
    public void importEngineerTrainings(File srcFile) {
        List<EngineerTraining> trainings = readFromExcel(srcFile, "发展", TRAINING_COLUMN, EngineerTraining.class);
        log.info("成功导入培训信息数：" + engineerTrainingManager.importEngineerTrainings(trainings));
    }

    @Override
    public void exportTrainings(File targetFile) {
        export2Excel(engineerTrainingManager.query(), "发展", EXCEL_TRAINING_HEADER, TRAINING_COLUMN, targetFile);
    }
}
