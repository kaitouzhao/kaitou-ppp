package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import com.womai.bsp.tool.utils.ExcelUtil;
import kaitou.ppp.domain.Engineer;
import kaitou.ppp.domain.EngineerTraining;
import kaitou.ppp.manager.EngineerManager;
import kaitou.ppp.service.EngineerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.ExcelUtil.readExcel;
import static com.womai.bsp.tool.utils.ExcelUtil.write2Excel;

/**
 * 工程师业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 23:16
 */
public class EngineerServiceImpl implements EngineerService {

    private final Log log = LogFactory.getLog(getClass());

    private EngineerManager engineerManager;

    private String dbDir;

    private static final String[] EXCEL_ENGINEER_HEADER = new String[]{"区域", "在职状态", "认定店名称", "认定店等级", "认定年限", "工程师编号", "工程师姓名", "ACE等级", "入职时间", "离职时间"};
    private static final String[] EXCEL_TRAINING_HEADER = new String[]{"区域", "产品线", "在职状态", "认定店名称", "认定店等级", "认定年限", "工程师编号", "工程师姓名", "ACE等级", "入职时间", "离职时间", "培训师", "培训类型", "培训时间", "培训机型"};
    private static final String[] ENGINEER_COLUMN = {"saleRegion", "status", "companyName", "companyLevel", "numberOfYear", "id", "name", "aceLevel", "dateOfEntry", "dateOfDeparture"};
    private static final String[] TRAINING_COLUMN = {"saleRegion", "productLine", "status", "companyName", "companyLevel", "numberOfYear", "id", "name", "aceLevel", "dateOfEntry", "dateOfDeparture", "trainer", "trainingType", "dateOfTraining", "trainingModel"};

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public void setEngineerManager(EngineerManager engineerManager) {
        this.engineerManager = engineerManager;
    }

    @Override
    public void importEngineers(File srcFile) {
        if (srcFile == null) {
            return;
        }
        InputStream is;
        try {
            is = new FileInputStream(srcFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<String[]> rows;
        try {
            rows = readExcel(is, "基础", 10, 2);
        } catch (Exception e) {
            throw new RuntimeException("导入的excel格式不正确，请检查");
        }
        if (CollectionUtil.isEmpty(rows)) {
            return;
        }
        List<Engineer> engineers = new ArrayList<Engineer>();
        for (String[] row : rows) {
            Engineer engineer = new Engineer();
            engineer.importFromArray(row, ENGINEER_COLUMN);
            engineers.add(engineer);
        }
        log.info("成功导入工程师数：" + engineerManager.importEngineers(engineers));
    }

    @Override
    public void exportEngineers(File targetFile) {
        if (targetFile == null) {
            return;
        }
        List<Object[]> datas = new ArrayList<Object[]>();
        Workbook wb;
        String basicSheet = "基础";
        List<Engineer> engineers = getAllEngineers();
        if (CollectionUtil.isEmpty(engineers)) {
            wb = write2Excel(basicSheet, EXCEL_ENGINEER_HEADER, datas, ExcelUtil.ExcelVersion.V2007);
        } else {
            for (Engineer engineer : engineers) {
                datas.add(engineer.export2Array(ENGINEER_COLUMN));
            }
            wb = write2Excel(basicSheet, EXCEL_ENGINEER_HEADER, datas, ExcelUtil.ExcelVersion.V2007);
        }
        writeExcelFile(targetFile, wb);
    }

    /**
     * 写excel文件
     *
     * @param targetFile 目标文件
     * @param wb         活动工作簿
     */
    private void writeExcelFile(File targetFile, Workbook wb) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(targetFile);
            wb.write(os);
            os.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 获取全部工程师列表
     *
     * @return 工程师列表
     */
    private List<Engineer> getAllEngineers() {
        File dbDirFile = new File(dbDir);
        File[] dbFiles = dbDirFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("_Engineer.kdb");
            }
        });
        List<Engineer> engineers = new ArrayList<Engineer>();
        for (File dbFile : dbFiles) {
            String dbFileName = dbFile.getName();
            String companyName = dbFileName.substring(dbFileName.indexOf('_', 1) + 1, dbFileName.lastIndexOf('_'));
            engineers.addAll(engineerManager.query(companyName));
        }
        return engineers;
    }

    @Override
    public void importEngineerTrainings(File srcFile) {
        if (srcFile == null) {
            return;
        }
        InputStream is;
        try {
            is = new FileInputStream(srcFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<String[]> rows;
        try {
            rows = readExcel(is, "发展", 15, 2);
        } catch (Exception e) {
            throw new RuntimeException("导入的excel格式不正确，请检查");
        }
        if (CollectionUtil.isEmpty(rows)) {
            return;
        }
        List<EngineerTraining> trainings = new ArrayList<EngineerTraining>();
        for (String[] row : rows) {
            EngineerTraining training = new EngineerTraining();
            training.importFromArray(row, TRAINING_COLUMN);
            trainings.add(training);
        }
        engineerManager.importEngineerTrainings(trainings);
        log.info("成功导入培训信息数：" + trainings.size());
    }

    @Override
    public void exportTrainings(File targetFile) {
        if (targetFile == null) {
            return;
        }
        List<Object[]> datas = new ArrayList<Object[]>();
        Workbook wb;
        String developmentSheet = "发展";
        List<Engineer> engineers = getAllEngineers();
        if (CollectionUtil.isEmpty(engineers)) {
            wb = write2Excel(developmentSheet, EXCEL_TRAINING_HEADER, datas, ExcelUtil.ExcelVersion.V2007);
        } else {
            for (Engineer engineer : engineers) {
                List<EngineerTraining> trainings = engineer.getEngineerTrainings();
                if (CollectionUtil.isEmpty(trainings)) {
                    continue;
                }
                for (EngineerTraining training : trainings) {
                    datas.add(training.export2Array(TRAINING_COLUMN));
                }
            }
            wb = write2Excel(developmentSheet, EXCEL_TRAINING_HEADER, datas, ExcelUtil.ExcelVersion.V2007);
        }
        writeExcelFile(targetFile, wb);
    }
}