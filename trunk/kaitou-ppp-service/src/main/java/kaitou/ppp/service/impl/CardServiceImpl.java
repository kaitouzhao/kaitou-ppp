package kaitou.ppp.service.impl;

import kaitou.ppp.domain.card.CardApplication;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.service.CardService;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保修卡处理业务层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 22:46
 */
public class CardServiceImpl implements CardService {

    private String workspace;
    private String complete;
    private static String output;
    private String logFileName;
    private String templateName;
    private static String template;

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public void generateCards() {
        String workspacePath = workspace;
        File workspace = new File(workspacePath);
        File[] appFiles = workspace.listFiles();
        String completePath = complete;
        List<Object[]> rowDataList = new ArrayList<Object[]>();
        List<Object[]> sheetDataList = new ArrayList<Object[]>();
        File log = new File(output + logFileName);
        DecimalFormat df = new DecimalFormat("00000");
        DateTime now = new DateTime();
        List<CardApplication> applications = new ArrayList<CardApplication>();
        for (int i = 0; i < appFiles.length; i++) {
            File appFile = appFiles[i];
            Workbook workbook = create(appFile);
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int j = 0; j < numberOfSheets; j++) {
                Sheet sheet = workbook.getSheetAt(j);
                CardApplication application = new CardApplication();
                application.fill(sheet);
                SysCode.Code code = SysCode.Code.getCode(application.getModels().substring(0, 1));
                String lastWarrantyCard = getLastRowCellStrValue(log, code.getCardNoPref(), 3, SysCode.CellType.STRING);
                long warrantyCardIndex = 0;
                if (lastWarrantyCard != null && !"".equals(lastWarrantyCard.trim())) {
                    warrantyCardIndex = Long.valueOf(lastWarrantyCard.substring(5));
                }
                application.setWarrantyCard(code.getCardNoPref() + "-A" + df.format(++warrantyCardIndex));
                application.setApplyDate(now.toString("yyyy/MM/dd"));
                DateTime installedDate = application.convertInstalledDate();
                DateTime endDate = installedDate.plusDays(364);
                if (endDate.isBeforeNow()) {
                    application.setStatus("过保");
                }
                application.setEndDate(endDate.toString("yyyy/MM/dd"));
                application.setAllModels(code.getModels());
                application.setInitData(application.getInitData() + code.getReadUnit());
//                System.out.println(application.toString());
                applications.add(application);
                rowDataList.add(application.getAllRowData());
                sheetDataList.add(application.getRowData());
                add2Sheet(log, code.getCardNoPref(), sheetDataList);
                sheetDataList.clear();
            }
            appFile.renameTo(new File(completePath + appFile.getName()));
        }
        add2Sheet(log, "汇总", rowDataList);
        transform2Xls(applications, templateName);
    }

    /**
     * 根据模板与数据转换excel
     *
     * @param applications 申请集合
     * @param templateName 模板名
     */
    private static void transform2Xls(List<CardApplication> applications, String templateName) {
        try {
            String templatePath = template + templateName;
//        String targetPath = getPath("output") + new DateTime().toString("yyyy_MM_dd") + ".xls";
//        multipleTransform(applications, templatePath, targetPath);
            Workbook wb;
            for (CardApplication application : applications) {
                String targetPath = output + application.getNewSheetName() + ".xls";
                wb = transform2Workbook(application, templatePath);
                OutputStream os = new BufferedOutputStream(new FileOutputStream(targetPath));
                wb.write(os);
                os.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 单个转换
     *
     * @param application  申请
     * @param templatePath 模板
     * @return 表
     */
    private static Workbook transform2Workbook(CardApplication application, String templatePath) {
        try {
            List<String> sheetNames = new ArrayList<String>();
            List<Map<String, Object>> fieldMap = new ArrayList<Map<String, Object>>();
            sheetNames.add(application.getNewSheetName());
            fieldMap.add(application.field2Map());
            File templateFile = new File(templatePath);
            InputStream is = new BufferedInputStream(new FileInputStream(templateFile));
            XLSTransformer transformer = new XLSTransformer();
            return transformer.transformMultipleSheetsList(is, fieldMap, sheetNames, "results_JxLsC_", new HashMap(), 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据不同版本excel文件创建工作簿
     *
     * @param file 文件
     * @return 工作簿
     */
    private static Workbook create(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            if (!is.markSupported()) {
                is = new PushbackInputStream(is, 8);
            }
            if (POIFSFileSystem.hasPOIFSHeader(is)) {
                return new HSSFWorkbook(is);
            }
            if (POIXMLDocument.hasOOXMLHeader(is)) {
                return new XSSFWorkbook(OPCPackage.open(is));
            }
            throw new RuntimeException("你的excel版本目前poi解析不了");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 获取最后一行单元格数据
     *
     * @param file        excel文件
     * @param sheetName   工作单元名
     * @param columnIndex 列序号
     * @param type        类型
     * @return 单元格数据
     */
    private static String getLastRowCellStrValue(File file, String sheetName, int columnIndex, SysCode.CellType type) {
        Workbook workbook = create(file);
        Sheet sheet = workbook.getSheet(sheetName);
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum < 1) {
            return "";
        }
        Row row = sheet.getRow(lastRowNum);
        return getStringCellValue(row.getCell(columnIndex), type);
    }

    /**
     * 将单元格数据转换成字符串
     *
     * @param cell 单元格
     * @param type 类型
     * @return 字符串
     */
    private static String getStringCellValue(Cell cell, SysCode.CellType type) {
        DecimalFormat df = new DecimalFormat("0");
        try {
            switch (type.getValue()) {
                case SysCode.CELL_TYPE_STRING:
                    return cell.getStringCellValue();
                case SysCode.CELL_TYPE_DATE:
                    return new DateTime(cell.getDateCellValue().getTime()).toString("yyyy/MM/dd");
                case SysCode.CELL_TYPE_NUMERIC:
                    return df.format(cell.getNumericCellValue());
                default:
                    return "";
            }
        } catch (Exception e) {
            if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                return df.format(cell.getNumericCellValue());
            }
            return "";
        }
    }

    /**
     * 加入到excel文件
     *
     * @param file      excel文件
     * @param sheetName 工作单元名
     * @param datas     新数据
     * @return 总行数
     */
    private static int add2Sheet(File file, String sheetName, List<Object[]> datas) {
        try {
            Workbook workbook = create(file);
            Sheet sheet = workbook.getSheet(sheetName);
            int allRow = sheet.getLastRowNum();
            Row row;
            for (Object[] data : datas) {
                if (data == null || data.length <= 0) {
                    continue;
                }
                row = sheet.createRow(++allRow);
                for (int j = 0; j < data.length; j++) {
                    row.createCell(j).setCellValue(String.valueOf(data[j] == null ? "" : data[j]));
                }
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                workbook.write(out);
                out.flush();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return allRow;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
