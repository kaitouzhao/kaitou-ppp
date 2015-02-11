/*
 * Created by JFormDesigner on Fri Feb 06 15:41:05 CST 2015
 */

package kaitou.ppp.app.ui.dialog;

import com.womai.bsp.tool.utils.CollectionUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * 输入对话框
 *
 * @author 赵立伟
 */
public class InputHint extends JDialog {
    /**
     * 输入框列表
     */
    private java.util.List<JTextField> textFields = new ArrayList<JTextField>();
    /**
     * 是否确定
     */
    private boolean isOk = false;

    public InputHint(Frame owner, String[] fields) {
        super(owner);
        initComponents();
        initInputArea(fields);
        setVisible(true);
    }

    /**
     * 初始化输入区域
     *
     * @param fields 属性名
     */
    private void initInputArea(String[] fields) {
        if (!CollectionUtil.isEmpty(fields)) {
            JLabel label;
            JTextField textField;
            for (String field : fields) {
                label = new JLabel(field);
                textField = new JTextField(10);
                inputArea.add(label);
                inputArea.add(textField);
                textFields.add(textField);
            }
        }
    }

    private InputHint(Dialog owner, String[] fields) {
        super(owner);
        initComponents();
        initInputArea(fields);
        setVisible(true);
    }

    private void okButtonActionPerformed() {
        isOk = true;
        setVisible(false);
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
     * 是否确定
     *
     * @return 是为真
     */
    public boolean isOk() {
        return isOk;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        inputArea = new JPanel();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setTitle("\u8bf7\u8f93\u5165");
        setModal(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new FlowLayout());

                //======== inputArea ========
                {
                    inputArea.setLayout(new FlowLayout());
                }
                contentPanel.add(inputArea);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed();
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane);
        dialogPane.setBounds(0, 0, 544, 394);

        contentPane.setPreferredSize(new Dimension(560, 430));
        setSize(560, 430);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel inputArea;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
