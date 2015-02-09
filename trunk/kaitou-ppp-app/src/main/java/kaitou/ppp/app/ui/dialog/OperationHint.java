/*
 * Created by JFormDesigner on Thu Feb 05 15:27:53 CST 2015
 */

package kaitou.ppp.app.ui.dialog;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.*;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 操作提示对话框
 *
 * @author kaitou zhao
 */
public class OperationHint extends JDialog {

    public OperationHint(Frame owner, String message) {
        super(owner);
        initComponents();
        messgeLab.setText(message);
        setVisible(true);
    }

    private OperationHint(Dialog owner, String message) {
        super(owner);
        initComponents();
        messgeLab.setText(message);
        setVisible(true);
    }

    private void okButtonActionPerformed(ActionEvent e) {
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        messgeLab = new JLabel();
        buttonBar = new JPanel();
        okButton = new JButton();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        setTitle("\u64cd\u4f5c\u63d0\u793a");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== dialogPane ========
        {
            dialogPane.setBorder(Borders.createEmptyBorder("7dlu, 7dlu, 7dlu, 7dlu"));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- messgeLab ----
                messgeLab.setText("text");
                contentPanel.add(messgeLab);
                messgeLab.setBounds(50, 52, 225, messgeLab.getPreferredSize().height);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(Borders.createEmptyBorder("5dlu, 0dlu, 0dlu, 0dlu"));
                buttonBar.setLayout(new FormLayout(
                        new ColumnSpec[]{
                                FormFactory.GLUE_COLSPEC,
                                FormFactory.BUTTON_COLSPEC
                        },
                        RowSpec.decodeSpecs("pref")
                ));

                //---- okButton ----
                okButton.setText("\u786e\u5b9a");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, cc.xy(2, 1));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane);
        dialogPane.setBounds(0, 0, 350, 165);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel messgeLab;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
