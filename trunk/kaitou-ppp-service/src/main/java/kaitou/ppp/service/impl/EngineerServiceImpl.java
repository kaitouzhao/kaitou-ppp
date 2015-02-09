package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.count.CountByProductLine;
import kaitou.ppp.domain.count.CountByShop;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.CachedShop;
import kaitou.ppp.domain.shop.CachedShopDetail;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.manager.engineer.EngineerManager;
import kaitou.ppp.manager.engineer.EngineerTrainingManager;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.EngineerService;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;

/**
 * 工程师业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 23:16
 */
public class EngineerServiceImpl extends BaseExcelService implements EngineerService {

    private EngineerManager engineerManager;
    private EngineerTrainingManager engineerTrainingManager;

    private ShopManager shopManager;

    private static final String[] IMPORT_ENGINEER_HEADER = new String[]{"产品线", "在职状态", "认定店编码", "认定店名称", "工程师编号", "工程师姓名", "ACE等级", "入职时间", "离职时间", "邮箱", "电话", "地址"};
    private static final String[] IMPORT_ENGINEER_COLUMN = {"productLine", "status", "shopId", "shopName", "id", "name", "aceLevel", "dateOfEntry", "dateOfDeparture", "email", "phone", "address"};

    private static final String[] IMPORT_TRAINING_HEADER = new String[]{"产品线", "工程师编号", "工程师姓名", "培训师", "培训类型", "培训时间", "培训机型"};
    private static final String[] IMPORT_TRAINING_COLUMN = {"productLine", "id", "name", "trainer", "trainingType", "dateOfTraining", "trainingModel"};

    private static final String[] EXPORT_ENGINEER_HEADER = new String[]{"区域", "产品线", "在职状态", "认定店编码", "认定店名称", "认定店等级", "认定年限", "工程师编号", "工程师姓名", "ACE等级", "入职时间", "离职时间", "邮箱", "电话", "地址"};
    private static final String[] EXPORT_ENGINEER_COLUMN = {"saleRegion", "productLine", "status", "shopId", "shopName", "shopLevel", "numberOfYear", "id", "name", "aceLevel", "dateOfEntry", "dateOfDeparture", "email", "phone", "address"};

    private static final String[] EXPORT_TRAINING_HEADER = new String[]{"区域", "产品线", "在职状态", "认定店编码", "认定店名称", "认定店等级", "认定年限", "工程师编号", "工程师姓名", "ACE等级", "入职时间", "离职时间", "培训师", "培训类型", "培训时间", "培训机型"};
    private static final String[] EXPORT_TRAINING_COLUMN = {"saleRegion", "productLine", "status", "shopId", "shopName", "shopLevel", "numberOfYear", "id", "name", "aceLevel", "dateOfEntry", "dateOfDeparture", "trainer", "trainingType", "dateOfTraining", "trainingModel"};

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setEngineerTrainingManager(EngineerTrainingManager engineerTrainingManager) {
        this.engineerTrainingManager = engineerTrainingManager;
    }

    public void setEngineerManager(EngineerManager engineerManager) {
        this.engineerManager = engineerManager;
    }

    @Override
    public void importEngineers(File srcFile) {
        List<Engineer> engineers = readFromExcel(srcFile, "基础", IMPORT_ENGINEER_HEADER, IMPORT_ENGINEER_COLUMN, Engineer.class);
        int successCount = 0;
        if (CollectionUtil.isEmpty(engineers)) {
            logOperation("成功导入工程师数：" + successCount);
            return;
        }
        for (Engineer engineer : engineers) {
            CachedShop cachedShop = shopManager.getCachedShop(engineer.getShopId());
            engineer.setSaleRegion(cachedShop.getSaleRegion());
            CachedShopDetail shopDetail = shopManager.getCachedShopDetail(engineer.getShopId(), engineer.getProductLine());
            engineer.setNumberOfYear(shopDetail.getNumberOfYear());
            engineer.setShopLevel(shopDetail.getLevel());
        }
        successCount = engineerManager.importEngineers(engineers);
        logOperation("成功导入工程师数：" + successCount);
    }

    @Override
    public void exportEngineers(File targetFile) {
        export2Excel(engineerManager.query(), "基础", EXPORT_ENGINEER_HEADER, EXPORT_ENGINEER_COLUMN, targetFile);
    }

    @Override
    public void importEngineerTrainings(File srcFile) {
        List<EngineerTraining> trainings = readFromExcel(srcFile, "发展", IMPORT_TRAINING_HEADER, IMPORT_TRAINING_COLUMN, EngineerTraining.class);
        int successCount = 0;
        if (CollectionUtil.isEmpty(trainings)) {
            logOperation("成功导入培训信息数：" + successCount);
            return;
        }
        List<Engineer> allEngineers = engineerManager.query();
        for (EngineerTraining training : trainings) {
            for (Engineer engineer : allEngineers) {
                if (!training.getId().equals(engineer.getId())) {
                    continue;
                }
                copyBean(engineer, training);
            }
        }
        successCount = engineerTrainingManager.importEngineerTrainings(trainings);
        logOperation("成功导入工程师培训信息数：" + successCount);
    }

    @Override
    public void exportTrainings(File targetFile) {
        export2Excel(engineerTrainingManager.query(), "发展", EXPORT_TRAINING_HEADER, EXPORT_TRAINING_COLUMN, targetFile);
    }

    @Override
    public void deleteEngineer(String saleRegion, String shopId, String id, String productLine) {
        Engineer deleted = new Engineer();
        deleted.setSaleRegion(saleRegion);
        deleted.setShopId(shopId);
        deleted.setId(id);
        deleted.setProductLine(productLine);
        logOperation("已删除工程师个数：" + engineerManager.delete(deleted));
    }

    @Override
    public void deleteEngineers(Object... engineers) {
        logOperation("已删除工程师个数：" + engineerManager.delete(engineers));
    }

    @Override
    public void deleteEngineerTraining(String saleRegion, String shopId, String id, String productLine, String trainingModel) {
        List<EngineerTraining> trainings = engineerTrainingManager.query(shopId);
        if (CollectionUtil.isEmpty(trainings)) {
            logOperation("已删除工程师发展信息个数：0");
            return;
        }
        List<EngineerTraining> deleted = new ArrayList<EngineerTraining>();
        for (EngineerTraining training : trainings) {
            if (training.getId().equals(id) && training.getProductLine().equals(productLine) && training.getTrainingModel().equals(trainingModel)) {
                deleted.add(training);
            }
        }
        logOperation("已删除工程师发展信息个数：" + engineerTrainingManager.delete(CollectionUtil.toArray(deleted, EngineerTraining.class)));
    }

    @Override
    public void deleteEngineerTrainings(Object... trainings) {
        logOperation("已删除工程师发展信息个数：" + engineerTrainingManager.delete(trainings));
    }

    @Override
    public void exportEngineers(File targetFile, String productLine) {
        List<Engineer> allEngineers = engineerManager.query();
        List<Engineer> engineers = new ArrayList<Engineer>();
        for (Engineer engineer : allEngineers) {
            if (SysCode.EngineerStatus.ON.getValue().equals(engineer.getStatus()) && engineer.getProductLine().equals(productLine)) {
                engineers.add(engineer);
            }
        }
        export2Excel(engineers, "基础", EXPORT_ENGINEER_HEADER, EXPORT_ENGINEER_COLUMN, targetFile);
    }

    @Override
    public void countEngineersByProductLine(File targetFile) {
        List<Engineer> allEngineers = engineerManager.query();
        List<CountByProductLine> result = new ArrayList<CountByProductLine>();
        CountByProductLine countByProductLine;
        String productLine;
        int index;
        for (Engineer engineer : allEngineers) {
            if (!SysCode.EngineerStatus.ON.getValue().equals(engineer.getStatus())) {
                continue;
            }
            productLine = engineer.getProductLine();
            index = result.indexOf(new CountByProductLine().setProductLine(productLine));
            if (index < 0) {
                countByProductLine = new CountByProductLine().setProductLine(productLine);
                result.add(countByProductLine);
            } else {
                countByProductLine = result.get(index);
            }
            countByProductLine.setCount(countByProductLine.getCount() + 1);
        }
        export2Excel(result, "产品线在职工程师统计", new String[]{"产品线", "人数"}, new String[]{"productLine", "count"}, targetFile);
    }

    @Override
    public void countEngineersByShop(String productLine, File targetFile) {
        List<Engineer> allEngineers = engineerManager.query();
        List<CountByShop> result = new ArrayList<CountByShop>();
        CountByShop countByShop;
        String shopId;
        String engineerProductLine;
        int index;
        for (Engineer engineer : allEngineers) {
            if (!SysCode.EngineerStatus.ON.getValue().equals(engineer.getStatus())) {
                continue;
            }
            if (!StringUtils.isEmpty(productLine)) {
                if (!productLine.equals(engineer.getProductLine())) {
                    continue;
                }
            }
            shopId = engineer.getShopId();
            engineerProductLine = engineer.getProductLine();
            index = result.indexOf(new CountByShop().setShopId(shopId).setProductLine(engineerProductLine));
            if (index < 0) {
                countByShop = new CountByShop().setShopId(shopId).setShopName(engineer.getShopName()).setProductLine(engineerProductLine);
                result.add(countByShop);
            } else {
                countByShop = result.get(index);
            }
            countByShop.setCount(countByShop.getCount() + 1);
        }
        export2Excel(result, "认定店在职工程师统计", new String[]{"认定店编码", "认定店名", "产品线", "人数"}, new String[]{"shopId", "shopName", "productLine", "count"}, targetFile);
    }

    @Override
    public List<Engineer> queryAllEngineers() {
        return engineerManager.query();
    }

    @Override
    public List<EngineerTraining> queryAllTrainings() {
        return engineerTrainingManager.query();
    }

    @Override
    public void editEngineer(Engineer engineer) {
        List<Engineer> engineers = new ArrayList<Engineer>();
        engineers.add(engineer);
        logOperation("成功更新工程师数：" + engineerManager.importEngineers(engineers));
    }
}
