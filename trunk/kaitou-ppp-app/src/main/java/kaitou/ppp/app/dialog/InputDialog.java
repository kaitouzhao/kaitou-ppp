package kaitou.ppp.app.dialog;

import com.womai.bsp.tool.utils.CollectionUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 输入操作对话框.
 * User: 赵立伟
 * Date: 2015/1/31
 * Time: 10:07
 */
public class InputDialog {
    /**
     * 对话框
     */
    private JDialog dialog;
    /**
     * 确定按钮
     */
    private JButton confirmBtn;
    /**
     * 是否确定
     */
    private boolean isOk = false;
    /**
     * 输入框列表
     */
    private List<JTextField> textFields = new ArrayList<JTextField>();

    /**
     * 初始化
     *
     * @param baseFrame 父窗
     * @param title     标题
     * @param fields    生成输入框名集合
     * @return 初始化后的对话框
     */
    public InputDialog(JFrame baseFrame, String title, String[] fields) {
        dialog = new JDialog(baseFrame, title, true);
        dialog.setBounds(400, 200, 350, 150);
        dialog.setLayout(new FlowLayout());
        dialog.setVisible(false);
        if (!CollectionUtil.isEmpty(fields)) {
            JLabel label;
            JTextField textField;
            for (String field : fields) {
                label = new JLabel(field);
                textField = new JTextField(10);
                dialog.add(label);
                dialog.add(textField);
                textFields.add(textField);
            }
        }
        confirmBtn = new JButton("确定");
        dialog.add(confirmBtn);
        myEvent();
        dialog.setVisible(true);
    }

    /**
     * 显示对话框
     */
    public void show() {
        isOk = false;
        dialog.setVisible(true);
    }

    /**
     * 是否确定删除
     *
     * @return 是为真
     */
    public boolean isOk() {
        return isOk;
    }

    /**
     * 获取输入结果集
     *
     * @return 结果集
     */
    public String[] getInputResult() {
        int size = textFields.size();
        String[] inputResults = new String[size];
        for (int i = 0; i < size; i++) {
            inputResults[i] = textFields.get(i).getText();
        }
        return inputResults;
    }

    /**
     * 事件
     */
    private void myEvent() {
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOk = true;
                dialog.setVisible(false);
            }
        });
    }
}
