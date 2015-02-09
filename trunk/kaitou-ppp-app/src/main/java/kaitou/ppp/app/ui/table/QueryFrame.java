/*
 * Created by JFormDesigner on Thu Feb 05 15:42:34 CST 2015
 */

package kaitou.ppp.app.ui.table;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.dialog.ConfirmHint;
import kaitou.ppp.app.ui.dialog.OperationHint;
import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static kaitou.ppp.app.ui.op.DeleteOp.delete;
import static kaitou.ppp.app.ui.op.EditOp.edit;
import static kaitou.ppp.common.utils.ReflectionUtil.getFieldValue;
import static kaitou.ppp.common.utils.ReflectionUtil.setFieldValue;

/**
 * 查询界面
 *
 * @author kaitou zhao
 */
public class QueryFrame extends JFrame {
    /**
     * 操作列标题
     */
    private static final String OP_COLUMN_TITLE = "操作";
    /**
     * 操作列号
     */
    private int opColumnIndex = -1;
    /**
     * 操作名
     * <p>只支持一组互斥操作</p>
     */
    private String[] opNames;
    /**
     * 操作属性
     * <p>只支持一组互斥操作</p>
     */
    private String opFieldName;
    /**
     * 当前frame
     */
    private QueryFrame self = this;
    /**
     * 标题集合
     */
    private String[] tableTitles;
    /**
     * 属性名集合
     */
    private String[] fieldNames;
    /**
     * 查询属性集合
     */
    private String[] queryFieldNames;
    /**
     * 查询区域输入框动态集合
     */
    private List<JTextField> queryTextFields = new ArrayList<JTextField>();
    /**
     * 数据源
     */
    private java.util.List<? extends BaseDomain> datas;
    /**
     * 显示的数据
     */
    private List shownDatas = new ArrayList();
    /**
     * 当前页码
     */
    private int currentPageIndex = 1;
    /**
     * 每页显示条数
     */
    private int countPerPage = 25;
    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 总记录数
     */
    private int recordCount;
    /**
     * 领域类型
     */
    private String domainType;

    /**
     * 构造器
     * <ul>
     * <li>参数赋值</li>
     * <li>初始化表格参数</li>
     * <li>初始化界面</li>
     * </ul>
     *
     * @param datas       数据源
     * @param queryObject 查询对象接口。提供查询所需参数
     */
    @SuppressWarnings(value = "unchecked")
    public QueryFrame(List<? extends BaseDomain> datas, IQueryObject queryObject) {
        this.tableTitles = queryObject.tableTitles();
        if (OP_COLUMN_TITLE.equals(tableTitles[tableTitles.length - 1])) {
            opColumnIndex = tableTitles.length - 1;
            opFieldName = queryObject.opFieldName();
            opNames = queryObject.opNames();
        }
        this.fieldNames = queryObject.fieldNames();
        this.queryFieldNames = queryObject.queryFieldNames();
        this.datas = datas;
        domainType = queryObject.domainType();
        shownDatas.addAll(datas);
        initComponents();
        initTableData();
        select();
        initQueryArea();
        setTitle(queryObject.title());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    /**
     * 初始化表格参数
     */
    @SuppressWarnings(value = "unchecked")
    private void initTableData() {
        recordCount = shownDatas.size();
        if (recordCount % countPerPage == 0) {
            pageCount = recordCount / countPerPage;
        } else {
            pageCount = (recordCount / countPerPage) + 1;
        }
        recordCountShow.setText("总计：" + recordCount);
        selectPage.removeAllItems();
        for (int i = 0; i < pageCount; i++) {
            selectPage.insertItemAt(i + 1, i);
        }
    }

    /**
     * 下一页
     */
    @SuppressWarnings(value = "unchecked")
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
    @SuppressWarnings(value = "unchecked")
    private void select() {
        List currentList = new ArrayList();
        for (int i = (currentPageIndex - 1) * countPerPage; i < currentPageIndex * countPerPage && i < recordCount; i++) {
            currentList.add(shownDatas.get(i));
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
        if (dataTable == null) {
            dataTable = new JTable(new QueryTable(objects,
                    tableTitles));
        } else {
            dataTable.setModel(new QueryTable(objects,
                    tableTitles));
        }
        if (opColumnIndex < 0) {
            return;
        }
        dataTable.getColumnModel().getColumn(opColumnIndex).setCellEditor(new QueryTableBtnCellEditor());
        dataTable.getColumnModel().getColumn(opColumnIndex).setCellRenderer(new QueryTableBtnCellEditor());
    }

    private void firstPageBtnActionPerformed() {
        currentPageIndex = 1;
        select();
    }

    private void previousPageBtnActionPerformed() {
        previousPage();
    }

    private void nextPageBtnActionPerformed() {
        nextPage();
    }

    private void thisWindowClosed() {
        setVisible(false);
    }

    @SuppressWarnings(value = "unchecked")
    private void queryBtnActionPerformed() {
        shownDatas.clear();
        if (!CollectionUtil.isEmpty(datas) && !CollectionUtil.isEmpty(queryTextFields)) {
            List<Integer> excludes = new ArrayList<Integer>();
            JTextField textField;
            String textFieldValue;
            for (int i = 0; i < datas.size(); i++) {
                for (int j = 0; j < queryTextFields.size(); j++) {
                    textField = queryTextFields.get(j);
                    textFieldValue = StringUtils.isEmpty(textField.getText()) ? "" : textField.getText().trim();
                    if (StringUtils.isEmpty(textFieldValue)) {
                        continue;
                    }
                    Object fieldValue = getFieldValue(queryFieldNames[j], datas.get(i));
                    if (fieldValue == null || fieldValue.toString().trim().equals("")) {
                        excludes.add(i);
                        break;
                    }
                    String fieldValueStr = fieldValue.toString();
                    if (!fieldValueStr.startsWith(textFieldValue.trim()) && !fieldValueStr.endsWith(textFieldValue.trim())) {
                        excludes.add(i);
                        break;
                    }
                }
            }
            for (int i = 0; i < datas.size(); i++) {
                if (excludes.contains(i)) {
                    continue;
                }
                shownDatas.add(datas.get(i));
            }
        } else {
            shownDatas.addAll(datas);
        }
        initTableData();
        currentPageIndex = 1;
        select();
    }

    private void selectPageActionPerformed() {
    }

    private void selectPageItemStateChanged(ItemEvent e) {
        currentPageIndex = Integer.valueOf(e.getItem().toString());
        select();
    }

    @SuppressWarnings(value = "unchecked")
    private void resetBtnActionPerformed() {
        for (JTextField queryTextField : queryTextFields) {
            queryTextField.setText("");
        }
        shownDatas.addAll(datas);
        queryBtnActionPerformed();
    }

    private void dataTableMouseClicked() {
        // TODO 暂未实现
    }

    private void deleteBtnActionPerformed() {
        int[] deleteIndexes = dataTable.getSelectedRows();
        if (deleteIndexes.length <= 0) {
            new OperationHint(this, "请先选择要删除的记录");
            return;
        }
        ConfirmHint hint = new ConfirmHint(this, "是否确认删除这些记录？");
        if (!hint.isOk()) {
            return;
        }
        Object[] deleted = new Object[deleteIndexes.length];
        for (int i = 0; i < deleteIndexes.length; i++) {
            int index = getShownDataIndex(deleteIndexes[i]);
            deleted[i] = shownDatas.get(index);
            shownDatas.remove(index);
            datas.remove(index);
        }
        delete(domainType, deleted);
        new OperationHint(this, "删除成功！");
        queryBtnActionPerformed();
    }

    /**
     * 自定义table
     *
     * @see javax.swing.table.DefaultTableModel
     */
    private class QueryTable extends DefaultTableModel {
        private QueryTable(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == opColumnIndex; // TODO 暂时只支持操作列可编辑。1.5版会支持部分列可编辑
        }

    }

    /**
     * 自定义table按钮列编辑器
     */
    private class QueryTableBtnCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer, ActionListener {

        private JButton btn = null;

        private QueryTableBtnCellEditor() {
            btn = new JButton("此处有操作");
            btn.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] opIndexes = dataTable.getSelectedRows();
            if (opIndexes.length <= 0) {
                return;
            }
            ConfirmHint hint = new ConfirmHint(self, "确定变更吗？");
            if (!hint.isOk()) {
                return;
            }
            Object shownObj = shownDatas.get(getShownDataIndex(opIndexes[0]));
            Object dataObj = datas.get(datas.indexOf(shownObj));
            String btnText = btn.getText();
            String changeValue;
            if (btnText.equals(opNames[0])) {
                changeValue = opNames[0];
            } else {
                changeValue = opNames[1];
            }
            setFieldValue(opFieldName, shownObj, changeValue);
            setFieldValue(opFieldName, dataObj, changeValue);
            edit(domainType, dataObj);
            new OperationHint(self, "操作成功");
            queryBtnActionPerformed();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            initBtn(row);
            return btn;
        }

        /**
         * 初始化按钮
         *
         * @param row 行数
         */
        private void initBtn(int row) {
            Object initFieldValue = getFieldValue(opFieldName, shownDatas.get(getShownDataIndex(row)));
            if (initFieldValue == null || opNames[1].equals(initFieldValue)) {
                btn.setText(opNames[0]);
                return;
            }
            btn.setText(opNames[1]);
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            initBtn(row);
            return btn;
        }
    }

    /**
     * 获取行号对应的显示数据位置
     *
     * @param tableRow 行号
     * @return 显示数据位置
     */
    private int getShownDataIndex(int tableRow) {
        return tableRow + (currentPageIndex - 1) * countPerPage;
    }

    /**
     * 初始化界面
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        resultArea = new JScrollPane();
        dataTable = new JTable();
        firstPageBtn = new JButton();
        previousPageBtn = new JButton();
        nextPageBtn = new JButton();
        recordCountShow = new JLabel();
        jump2Page = new JLabel();
        selectPage = new JComboBox();
        queryArea = new JPanel();
        queryBtn = new JButton();
        resetBtn = new JButton();
        deleteBtn = new JButton();

        //======== this ========
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed();
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== resultArea ========
        {
            resultArea.setAutoscrolls(true);

            //---- dataTable ----
            dataTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dataTableMouseClicked();
                }
            });
            resultArea.setViewportView(dataTable);
        }
        contentPane.add(resultArea);
        resultArea.setBounds(0, 145, 1366, 485);

        //---- firstPageBtn ----
        firstPageBtn.setText("\u9996\u9875");
        firstPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstPageBtnActionPerformed();
            }
        });
        contentPane.add(firstPageBtn);
        firstPageBtn.setBounds(new Rectangle(new Point(305, 635), firstPageBtn.getPreferredSize()));

        //---- previousPageBtn ----
        previousPageBtn.setText("\u4e0a\u4e00\u9875");
        previousPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousPageBtnActionPerformed();
            }
        });
        contentPane.add(previousPageBtn);
        previousPageBtn.setBounds(new Rectangle(new Point(375, 635), previousPageBtn.getPreferredSize()));

        //---- nextPageBtn ----
        nextPageBtn.setText("\u4e0b\u4e00\u9875");
        nextPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextPageBtnActionPerformed();
            }
        });
        contentPane.add(nextPageBtn);
        nextPageBtn.setBounds(new Rectangle(new Point(455, 635), nextPageBtn.getPreferredSize()));

        //---- recordCountShow ----
        recordCountShow.setText("\u603b\u8ba1\uff1a");
        contentPane.add(recordCountShow);
        recordCountShow.setBounds(5, 640, 120, recordCountShow.getPreferredSize().height);

        //---- jump2Page ----
        jump2Page.setText("\u8df3\u8f6c\u5230\uff1a");
        contentPane.add(jump2Page);
        jump2Page.setBounds(new Rectangle(new Point(155, 640), jump2Page.getPreferredSize()));

        //---- selectPage ----
        selectPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPageActionPerformed();
            }
        });
        selectPage.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectPageItemStateChanged(e);
            }
        });
        contentPane.add(selectPage);
        selectPage.setBounds(205, 635, 90, selectPage.getPreferredSize().height);

        //======== queryArea ========
        {
            queryArea.setAutoscrolls(true);
            queryArea.setLayout(new FlowLayout(FlowLayout.LEFT));
        }
        contentPane.add(queryArea);
        queryArea.setBounds(0, 5, 1366, 95);

        //---- queryBtn ----
        queryBtn.setText("\u68c0\u7d22");
        queryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryBtnActionPerformed();
            }
        });
        contentPane.add(queryBtn);
        queryBtn.setBounds(new Rectangle(new Point(70, 110), queryBtn.getPreferredSize()));

        //---- resetBtn ----
        resetBtn.setText("\u91cd\u7f6e");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBtnActionPerformed();
            }
        });
        contentPane.add(resetBtn);
        resetBtn.setBounds(new Rectangle(new Point(5, 110), resetBtn.getPreferredSize()));

        //---- deleteBtn ----
        deleteBtn.setText("\u5220\u9664\u9009\u4e2d\u7684\u8bb0\u5f55");
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBtnActionPerformed();
            }
        });
        contentPane.add(deleteBtn);
        deleteBtn.setBounds(new Rectangle(new Point(765, 635), deleteBtn.getPreferredSize()));

        contentPane.setPreferredSize(new Dimension(920, 695));
        setSize(920, 695);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

    }

    /**
     * 初始化查询区域
     */
    private void initQueryArea() {
        if (CollectionUtil.isEmpty(queryFieldNames)) {
            return;
        }
        JLabel queryLabel;
        JTextField queryTextField;
        String queryLabelTitle = null;
        for (String queryFieldName : queryFieldNames) {
            for (int j = 0; j < fieldNames.length; j++) {
                if (queryFieldName.equals(fieldNames[j])) {
                    queryLabelTitle = tableTitles[j];
                    break;
                }
            }
            if (StringUtils.isEmpty(queryLabelTitle)) {
                continue;
            }
            queryLabel = new JLabel(queryLabelTitle);
            queryArea.add(queryLabel);
            queryTextField = new JTextField(10);
            queryArea.add(queryTextField);
            queryTextFields.add(queryTextField);
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane resultArea;
    private JTable dataTable;
    private JButton firstPageBtn;
    private JButton previousPageBtn;
    private JButton nextPageBtn;
    private JLabel recordCountShow;
    private JLabel jump2Page;
    private JComboBox selectPage;
    private JPanel queryArea;
    private JButton queryBtn;
    private JButton resetBtn;
    private JButton deleteBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
