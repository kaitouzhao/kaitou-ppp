/*
 * Created by JFormDesigner on Sat Apr 11 15:05:46 CST 2015
 */

package kaitou.ppp.app.ui.dialog;

import com.womai.bsp.tool.utils.CollectionUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static kaitou.ppp.app.SpringContextManager.getLocalRegistryService;
import static kaitou.ppp.app.SpringContextManager.getSystemSettingsService;

/**
 * 联机配置
 *
 * @author kaitou
 */
public class OnlineConfig extends JDialog {

    public OnlineConfig(Frame owner, boolean isOnline, boolean syncOk) {
        super(owner);
        initComponents();
        if (isOnline) {
            onlineStatus.setText("是");
        }
        if (syncOk) {
            syncStatus.setText("是");
        }
        initConfig();

        setVisible(true);
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        localIP.setText(getSystemSettingsService().getLocalIp());
        java.util.List<String> ips = getLocalRegistryService().queryRegistryIps();
        if (CollectionUtil.isNotEmpty(ips)) {
            hostIP.setText(ips.get(0));
            StringBuilder ipInfo = new StringBuilder();
            for (int i = 0; i < ips.size(); i++) {
                if (i > 0) {
                    ipInfo.append(",");
                }
                ipInfo.append(ips.get(i));
            }
            registryIPs.setText(ipInfo.toString());
        }
    }

    public OnlineConfig(Dialog owner) {
        super(owner);
        initComponents();
    }

    private void okButtonActionPerformed(ActionEvent e) {
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        onlineStatus = new JLabel();
        label2 = new JLabel();
        syncStatus = new JLabel();
        label3 = new JLabel();
        hostIP = new JLabel();
        label4 = new JLabel();
        registryIPs = new JLabel();
        label5 = new JLabel();
        localIP = new JLabel();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setTitle("\u8054\u673a\u67e5\u770b");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- label1 ----
                label1.setText("\u662f\u5426\u8054\u673a\uff1a");
                contentPanel.add(label1);
                label1.setBounds(15, 15, 70, 25);

                //---- onlineStatus ----
                onlineStatus.setText("\u5426");
                contentPanel.add(onlineStatus);
                onlineStatus.setBounds(new Rectangle(new Point(85, 20), onlineStatus.getPreferredSize()));

                //---- label2 ----
                label2.setText("\u662f\u5426\u540c\u6b65\u6570\u636e\uff1a");
                contentPanel.add(label2);
                label2.setBounds(new Rectangle(new Point(165, 20), label2.getPreferredSize()));

                //---- syncStatus ----
                syncStatus.setText("\u5426");
                contentPanel.add(syncStatus);
                syncStatus.setBounds(new Rectangle(new Point(260, 20), syncStatus.getPreferredSize()));

                //---- label3 ----
                label3.setText("\u4e3b\u673aIP\uff1a");
                contentPanel.add(label3);
                label3.setBounds(new Rectangle(new Point(15, 55), label3.getPreferredSize()));

                //---- hostIP ----
                hostIP.setText("\u65e0");
                contentPanel.add(hostIP);
                hostIP.setBounds(80, 55, 80, hostIP.getPreferredSize().height);

                //---- label4 ----
                label4.setText("\u5df2\u6ce8\u518cIP\uff1a");
                contentPanel.add(label4);
                label4.setBounds(new Rectangle(new Point(15, 90), label4.getPreferredSize()));

                //---- registryIPs ----
                registryIPs.setText("\u65e0");
                contentPanel.add(registryIPs);
                registryIPs.setBounds(80, 90, 265, registryIPs.getPreferredSize().height);

                //---- label5 ----
                label5.setText("\u672c\u673aIP\uff1a");
                contentPanel.add(label5);
                label5.setBounds(new Rectangle(new Point(165, 55), label5.getPreferredSize()));

                //---- localIP ----
                localIP.setText("\u65e0");
                contentPanel.add(localIP);
                localIP.setBounds(255, 55, 85, localIP.getPreferredSize().height);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel onlineStatus;
    private JLabel label2;
    private JLabel syncStatus;
    private JLabel label3;
    private JLabel hostIP;
    private JLabel label4;
    private JLabel registryIPs;
    private JLabel label5;
    private JLabel localIP;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
