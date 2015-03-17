/*
 * Created by JFormDesigner on Thu Feb 05 15:27:53 CST 2015
 */

package kaitou.ppp.app.ui.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * 操作提示对话框
 *
 * @author kaitou zhao
 */
public class OperationHint extends JDialog {

    public OperationHint(Frame owner, String message) {
        super(owner);
        initComponents();
        messageLab.setText(message);
        setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        messageLab = new JTextArea();

        //======== this ========
        setTitle("\u64cd\u4f5c\u63d0\u793a");
        setModal(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== scrollPane1 ========
        {

            //---- messageLab ----
            messageLab.setEditable(false);
            scrollPane1.setViewportView(messageLab);
        }
        contentPane.add(scrollPane1, BorderLayout.CENTER);
        setSize(265, 185);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane1;
    private JTextArea messageLab;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
