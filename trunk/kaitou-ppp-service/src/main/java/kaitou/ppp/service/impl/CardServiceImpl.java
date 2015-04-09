package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.card.CardApplication;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.manager.card.CardApplicationRecordManager;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.CardService;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang.StringUtils;
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

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;

/**
 * 保修卡处理业务层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 22:46
 */
public class CardServiceImpl extends BaseExcelService implements CardService {

    private String workspace;
    private String complete;
    private String output;
    private String logFileName;
    private String templateName;
    private String template;

    private CardApplicationRecordManager cardApplicationRecordManager;
    private ShopManager shopManager;

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setCardApplicationRecordManager(CardApplicationRecordManager cardApplicationRecordManager) {
        this.cardApplicationRecordManager = cardApplicationRecordManager;
    }

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
        if (appFiles == null) {
            return;
        }
        String completePath = complete;
        List<Object[]> rowDataList = new ArrayList<Object[]>();
        List<Object[]> sheetDataList = new ArrayList<Object[]>();
        File log = new File(output + logFileName);
        DecimalFormat df = new DecimalFormat("00000");
        DateTime now = new DateTime();
        List<CardApplication> applications = new ArrayList<CardApplication>();
        List<CardApplicationRecord> cardApplicationRecords = new ArrayList<CardApplicationRecord>();
        List<Shop> shops = shopManager.query();
        for (File appFile : appFiles) {
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
                String shopName = application.getServiceCompanyName();
                String shopId = null;
                for (Shop shop : shops) {
                    if (shop.getName().equals(shopName)) {
                        shopId = shop.getId();
                        break;
                    }
                }
                if (shopId == null) {
                    throw new RuntimeException("保修卡的认定店：" + shopName + "不存在！请检查");
                }
                CardApplicationRecord cardApplicationRecord = new CardApplicationRecord();
                copyBean(application, cardApplicationRecord);
                cardApplicationRecord.setShopName(shopName);
                cardApplicationRecord.setShopId(shopId);
                cardApplicationRecords.add(cardApplicationRecord);
                rowDataList.add(application.getAllRowData());
                sheetDataList.add(application.getRowData());
                add2Sheet(log, code.getCardNoPref(), sheetDataList);
                sheetDataList.clear();
            }
            boolean b = appFile.renameTo(new File(completePath + appFile.getName()));
            if (!b) {
                throw new RuntimeException("生成保修卡失败");
            }
        }
        add2Sheet(log, "汇总", rowDataList);
        logOperation("成功导入/更新保修卡生成记录数：" + cardApplicationRecordManager.save(cardApplicationRecords));
        transform2Xls(applications, templateName);
    }

    /**
     * 根据模板与数据转换excel
     *
     * @param applications 申请集合
     * @param templateName 模板名
     */
    private void transform2Xls(List<CardApplication> applications, String templateName) {
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
    private Workbook transform2Workbook(CardApplication application, String templatePath) {
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
    private Workbook create(File file) {
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
                    logSystemEx(e);
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
    private String getLastRowCellStrValue(File file, String sheetName, int columnIndex, SysCode.CellType type) {
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
    private String getStringCellValue(Cell cell, SysCode.CellType type) {
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
    private int add2Sheet(File file, String sheetName, List<Object[]> datas) {
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

    @Override
    public void importCardApplicationRecords(File srcFile) {
        List<CardApplicationRecord> cardApplicationRecords = readFromExcel(srcFile, CardApplicationRecord.class);
        List<Shop> shops = shopManager.query();
        for (int i = 0, cardApplicationRecordsSize = cardApplicationRecords.size(); i < cardApplicationRecordsSize; i++) {
            CardApplicationRecord cardApplicationRecord = cardApplicationRecords.get(i);
            for (Shop shop : shops) {
                if (shop.getName().equals(cardApplicationRecord.getShopName())) {
                    cardApplicationRecord.setShopId(shop.getId());
                    break;
                }
            }
            if (StringUtils.isEmpty(cardApplicationRecord.getShopId())) {
                throw new RuntimeException("第" + (i + 2) + "行保修卡记录销售单位【" + cardApplicationRecord.getShopName() + "】不是认定店！");
            }
        }
        logOperation("成功导入/更新保修卡生成记录数：" + cardApplicationRecordManager.save(cardApplicationRecords));
    }

    @Override
    public void exportCardApplicationRecords(File targetFile) {
        export2Excel(cardApplicationRecordManager.query(), targetFile, CardApplicationRecord.class);
    }

    @Override
    public List<CardApplicationRecord> queryCardApplicationRecords() {
        return cardApplicationRecordManager.query();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateCardApplicationRecord(CardApplicationRecord cardApplicationRecord) {
        List<Shop> shops = shopManager.query();
        for (Shop shop : shops) {
            if (shop.getName().equals(cardApplicationRecord.getShopName())) {
                cardApplicationRecord.setShopId(shop.getId());
                break;
            }
        }
        if (StringUtils.isEmpty(cardApplicationRecord.getShopId())) {
            throw new RuntimeException("认定店：" + cardApplicationRecord.getShopName() + "不存在！请检查");
        }
        logOperation("成功导入/更新保修卡生成记录数：" + cardApplicationRecordManager.save(CollectionUtil.newList(cardApplicationRecord)));
    }

    @Override
    public void deleteCardApplicationRecords(Object... cardApplicationRecords) {
        logOperation("成功删除保修卡生成记录个数：" + cardApplicationRecordManager.delete(cardApplicationRecords));
    }
}
