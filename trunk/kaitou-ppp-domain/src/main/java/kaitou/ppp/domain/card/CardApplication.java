package kaitou.ppp.domain.card;

import kaitou.ppp.domain.system.SysCode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.DateTime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保修卡申请.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 22:36
 */
public class CardApplication {

    private String applyDate;
    @Coordinates(x = {10, 11}, y = {25, 34})
    private String serviceCompanyCode;
    @Coordinates(x = {13, 14}, y = {6, 13})
    private String serviceCompanyName;
    @Coordinates(x = {13, 14}, y = {24, 34})
    private String serviceLinkMan;
    @Coordinates(x = {17, 17}, y = {6, 20})
    private String serviceLinkEmail;
    @Coordinates(x = {17, 17}, y = {24, 34})
    private String servicePhoneNumber;

    @Coordinates(x = {25, 26}, y = {9, 34})
    private String userCompanyName;
    @Coordinates(x = {28, 29}, y = {7, 34})
    private String installedAddress;
    @Coordinates(x = {31, 32}, y = {7, 18})
    private String userLinkMan;
    @Coordinates(x = {31, 32}, y = {25, 34})
    private String userContact;

    @Coordinates(x = {37, 38}, y = {7, 18})
    private String models;
    @Coordinates(x = {37, 38}, y = {23, 33})
    private String fuselage;
    @Coordinates(x = {40, 41}, y = {7, 18}, type = SysCode.CellType.DATE)
    private String installedDate;
    @Coordinates(x = {40, 41}, y = {23, 33})
    private String initData;
    @Coordinates(x = {44, 45}, y = {17, 33}, type = SysCode.CellType.DATE)
    private String outputDate;

    @Coordinates(x = {50, 51}, y = {9, 33})
    private String macAddress;

    private String warrantyCard;
    private String endDate;
    private String status = "保修中";
    private String allModels;

    /**
     * 获取保修卡sheet名
     *
     * @return sheet名
     */
    public String getNewSheetName() {
        return models + '（' + warrantyCard + "）";
    }

    /**
     * 获取汇总行数据
     *
     * @return 行数据
     */
    public Object[] getAllRowData() {
        List<Object> rowData = new ArrayList<Object>();
        rowData.add(applyDate);//申请日期
        rowData.add(status);//状态
        rowData.add("");//是否寄回
        rowData.add(warrantyCard);//保修卡号
        rowData.add(allModels);//机型
        rowData.add(models);//产品型号
        rowData.add(fuselage);//机身号
        rowData.add(serviceCompanyName);//销售单位
        rowData.add(installedDate);//装机时间
        rowData.add(endDate);//到期日期
        rowData.add(initData);//初始读数
        rowData.add(userCompanyName);//最终用户
        rowData.add(userLinkMan);//联系人
        rowData.add(userContact);//联系方式
        rowData.add(installedAddress);//联系地址
        return rowData.toArray();
    }

    /**
     * 获取分页行数据
     *
     * @return 行数据
     */
    public Object[] getRowData() {
        List<Object> rowData = new ArrayList<Object>();
        rowData.add(applyDate);//申请日期
        rowData.add(status);//状态
        rowData.add("");//是否寄回
        rowData.add(warrantyCard);//保修卡号
        rowData.add(models);//产品型号
        rowData.add(fuselage);//机身号
        rowData.add(serviceCompanyName);//销售单位
        rowData.add(installedDate);//装机时间
        rowData.add(endDate);//到期日期
        rowData.add(initData);//初始读数
        rowData.add(userCompanyName);//最终用户
        rowData.add(userLinkMan);//联系人
        rowData.add(userContact);//联系方式
        rowData.add(installedAddress);//联系地址
        return rowData.toArray();
    }

    /**
     * 属性转换map
     *
     * @return 属性map
     */
    public Map<String, Object> field2Map() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                StringBuilder getterMethod = new StringBuilder("get")
                        .append(fieldName.substring(0, 1).toUpperCase())
                        .append(fieldName.substring(1));
                Method method = getClass().getMethod(getterMethod.toString());
                map.put(fieldName, method.invoke(this));
            }
            map.put("newSheetName", getNewSheetName());
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 填充对象
     *
     * @param sheet 读取sheet
     * @return 填充后的对象
     */
    public CardApplication fill(Sheet sheet) {
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            if (declaredAnnotations == null || declaredAnnotations.length <= 0) {
                continue;
            }
            Coordinates coordinates = (Coordinates) declaredAnnotations[0];
            String fieldName = field.getName();
            StringBuilder setterMethod = new StringBuilder("set")
                    .append(fieldName.substring(0, 1).toUpperCase())
                    .append(fieldName.substring(1));
            try {
                Method method = this.getClass().getMethod(setterMethod.toString(), String.class);
                method.invoke(this, getMergedRegions(sheet, coordinates.x(), coordinates.y(), coordinates.type()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }

    /**
     * 获取合并单元格数据
     *
     * @param sheet
     * @param x        横坐标范围
     * @param y        纵坐标范围
     * @param cellType 数据类型
     * @return 单元格数据
     */
    public static String getMergedRegions(Sheet sheet, int[] x, int[] y, SysCode.CellType cellType) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (x[0] == firstRow && x[1] == lastRow) {
                if (y[0] >= firstColumn && y[1] <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);

                    return getStringCellValue(fCell, cellType);
                }
            }
        }
        return "";
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

    @Override
    public String toString() {
        return "Application{" +
                "applyDate='" + applyDate + '\'' +
                ", serviceCompanyCode='" + serviceCompanyCode + '\'' +
                ", serviceCompanyName='" + serviceCompanyName + '\'' +
                ", serviceLinkMan='" + serviceLinkMan + '\'' +
                ", serviceLinkEmail='" + serviceLinkEmail + '\'' +
                ", servicePhoneNumber='" + servicePhoneNumber + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", installedAddress='" + installedAddress + '\'' +
                ", userLinkMan='" + userLinkMan + '\'' +
                ", userContact='" + userContact + '\'' +
                ", models='" + models + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", installedDate='" + installedDate + '\'' +
                ", initData='" + initData + '\'' +
                ", outputDate='" + outputDate + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", warrantyCard='" + warrantyCard + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", allModels='" + allModels + '\'' +
                '}';
    }

    public DateTime convertInstalledDate() {
        String[] split = installedDate.split("/");
        return new DateTime(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]), 0, 0);
    }

    public String getAllModels() {
        return allModels;
    }

    public void setAllModels(String allModels) {
        this.allModels = allModels;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getWarrantyCard() {
        return warrantyCard;
    }

    public void setWarrantyCard(String warrantyCard) {
        this.warrantyCard = warrantyCard;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getServiceCompanyCode() {
        return serviceCompanyCode;
    }

    public void setServiceCompanyCode(String serviceCompanyCode) {
        this.serviceCompanyCode = serviceCompanyCode;
    }

    public String getServiceCompanyName() {
        return serviceCompanyName;
    }

    public void setServiceCompanyName(String serviceCompanyName) {
        this.serviceCompanyName = serviceCompanyName;
    }

    public String getServiceLinkMan() {
        return serviceLinkMan;
    }

    public void setServiceLinkMan(String serviceLinkMan) {
        this.serviceLinkMan = serviceLinkMan;
    }

    public String getServiceLinkEmail() {
        return serviceLinkEmail;
    }

    public void setServiceLinkEmail(String serviceLinkEmail) {
        this.serviceLinkEmail = serviceLinkEmail;
    }

    public String getServicePhoneNumber() {
        return servicePhoneNumber;
    }

    public void setServicePhoneNumber(String servicePhoneNumber) {
        this.servicePhoneNumber = servicePhoneNumber;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getInstalledAddress() {
        return installedAddress;
    }

    public void setInstalledAddress(String installedAddress) {
        this.installedAddress = installedAddress;
    }

    public String getUserLinkMan() {
        return userLinkMan;
    }

    public void setUserLinkMan(String userLinkMan) {
        this.userLinkMan = userLinkMan;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getFuselage() {
        return fuselage;
    }

    public void setFuselage(String fuselage) {
        this.fuselage = fuselage;
    }

    public String getInstalledDate() {
        return installedDate;
    }

    public void setInstalledDate(String installedDate) {
        this.installedDate = installedDate;
    }

    public String getInitData() {
        return initData;
    }

    public void setInitData(String initData) {
        this.initData = initData;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
