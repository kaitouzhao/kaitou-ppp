package kaitou.ppp.service;

import com.womai.bsp.tool.utils.CollectionUtil;
import com.womai.bsp.tool.utils.ExcelUtil;
import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.domain.BaseDomain;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.ExcelUtil.readExcel;
import static com.womai.bsp.tool.utils.ExcelUtil.readExcelIsLegal;
import static com.womai.bsp.tool.utils.ExcelUtil.write2Excel;

/**
 * excel操作业务层.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 13:26
 */
public abstract class BaseExcelService extends BaseLogManager {

    /**
     * 将excel数据转换成实体
     *
     * @param srcFile     源文件
     * @param sheetName   读取sheet名
     * @param headers     excel标题
     * @param columns     excel列与实体属性对应
     * @param domainClass 实体类型   @return 实体列表
     */
    protected <T extends BaseDomain> List<T> readFromExcel(File srcFile, String sheetName, String[] headers, String[] columns, Class<T> domainClass) {
        List<T> domainList = new ArrayList<T>();
        if (srcFile == null) {
            return domainList;
        }
        if (!readExcelIsLegal(srcFile, sheetName, headers)) {
            StringBuilder logInfo = new StringBuilder("导入的excel格式不正确。请检查以下内容：sheet名（正确应该是：");
            logInfo.append(sheetName).append("）、列名（正确应该是：");
            for (String header : headers) {
                logInfo.append(header).append(" ");
            }
            logInfo.append("）");
            logOperation(logInfo.toString());
            throw new RuntimeException("导入的excel格式不正确，请检查");
        }
        InputStream is;
        try {
            is = new FileInputStream(srcFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<String[]> rows;
        try {
            rows = readExcel(is, sheetName, headers.length, 2);
        } catch (Exception e) {
            logOperation("导入的excel格式不正确，请检查");
            throw new RuntimeException("导入的excel格式不正确，请检查", e);
        }
        if (CollectionUtil.isEmpty(rows)) {
            return domainList;
        }
        for (String[] row : rows) {
            T domain;
            try {
                domain = domainClass.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            domain.importFromArray(row, columns);
            domainList.add(domain);
        }
        return domainList;
    }

    /**
     * 写excel文件
     *
     * @param targetFile 目标文件
     * @param wb         活动工作簿
     */
    protected void writeExcelFile(File targetFile, Workbook wb) {
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
     * 导出至excel
     *
     * @param domainList 实体列表
     * @param sheetName  写入的sheet名
     * @param headers    excel标题
     * @param columns    excel列与实体属性对应
     * @param targetFile 目标文件
     * @param <T>
     */
    protected <T extends BaseDomain> void export2Excel(List<T> domainList, String sheetName, String[] headers, String[] columns, File targetFile) {
        if (targetFile == null) {
            return;
        }
        List<Object[]> datas = new ArrayList<Object[]>();
        Workbook wb;
        if (CollectionUtil.isEmpty(domainList)) {
            wb = write2Excel(sheetName, headers, datas, ExcelUtil.ExcelVersion.V2007);
        } else {
            for (T domain : domainList) {
                datas.add(domain.export2Array(columns));
            }
            wb = write2Excel(sheetName, headers, datas, ExcelUtil.ExcelVersion.V2007);
        }
        writeExcelFile(targetFile, wb);
    }

}
