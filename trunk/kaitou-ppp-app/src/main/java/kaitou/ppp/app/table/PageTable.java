package kaitou.ppp.app.table;

import kaitou.ppp.app.BaseForm;
import kaitou.ppp.domain.BaseDomain;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 分页表格.
 * User: 赵立伟
 * Date: 2015/2/4
 * Time: 14:24
 */
@Deprecated
public class PageTable extends BaseForm {
    /**
     * 表格
     */
    private JTable table;
    /**
     * 第一页按钮
     */
    private JButton firstPageBtn = new JButton("第一页");
    /**
     * 上一页按钮
     */
    private JButton previousPageBtn = new JButton("上一页");
    /**
     * 下一页按钮
     */
    private JButton nextPageBtn = new JButton("下一页");

    /**
     * 标题集合
     */
    private String[] columnNames;
    /**
     * 属性名集合
     */
    private String[] fieldNames;
    /**
     * 数据源
     */
    private List<? extends BaseDomain> datas;
    /**
     * 当前页码
     */
    private int currentPageIndex = 1;
    /**
     * 每页显示条数
     */
    private int countPerPage = 10;
    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 总记录数
     */
    private int recordCount;

    public PageTable(String[] columnNames, String[] fieldNames, List<? extends BaseDomain> datas) {
        this.columnNames = columnNames;
        this.fieldNames = fieldNames;
        this.datas = datas;
        this.recordCount = datas.size();
        if (recordCount % countPerPage == 0) {
            pageCount = recordCount / countPerPage;
        } else {
            pageCount = (recordCount / countPerPage) + 1;
        }
        initComponent();
    }

    /**
     * 初始化窗体组件
     */
    private void initComponent() {
//        setCurrentPageIndex();
        view(datas);
        JScrollPane scroll = new JScrollPane(table);
        mainFrame.add(scroll);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    /**
     * 设置当前页
     */
    private void setCurrentPageIndex() {
        select();
    }

    /**
     * 下一页
     */
    private void nextPage() {
        if (currentPageIndex < pageCount) {
            currentPageIndex++;
        }
        select();
    }

    /**
     * 上一页
     */
    private void previousPage() {
        if (currentPageIndex > 1) {
            currentPageIndex--;
        }
        select();
    }

    /**
     * 选择
     */
    private void select() {
        List currentList = new ArrayList();
        for (int i = (currentPageIndex - 1) * countPerPage; i < currentPageIndex * countPerPage && i < recordCount; i++) {
            currentList.add(datas.get(i));
        }
        view(currentList);
    }

    /**
     * 显示
     *
     * @param list 当前显示列表
     */
    private void view(List<? extends BaseDomain> list) {
        Object[][] objects = new Object[(list.size())][fieldNames.length];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = list.get(i).export2Array(fieldNames);
        }
        if (table == null) {
            table = new JTable(new DefaultTableModel(objects,
                    columnNames));
        } else {
            table.setModel(new DefaultTableModel(objects,
                    columnNames));
        }
    }
}
