package kaitou.ppp.app.menu;

import kaitou.ppp.app.BaseForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static com.womai.bsp.tool.utils.PropertyUtil.getValue;

/**
 * 帮助菜单管理.
 * User: 赵立伟
 * Date: 2015/1/30
 * Time: 21:47
 */
@Deprecated
public class HelpMenuManager extends BaseForm {
    /**
     * 帮助管理
     */
    public static JMenu helpMenu = new JMenu("帮助");
    /**
     * 版本管理
     */
    private static JMenuItem aboutItem = new JMenuItem("关于");
    /**
     * 备份数据
     */
    private static JMenuItem backupItem = new JMenuItem("备份数据");
    /**
     * 恢复数据
     */
    private static JMenuItem recoveryItem = new JMenuItem("恢复数据");

    private static JDialog dialog = new JDialog(mainFrame, "帮助", true);
    private static JLabel label = new JLabel();

    static {
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();
        helpMenu.add(backupItem);
        helpMenu.addSeparator();
        helpMenu.add(recoveryItem);
        helpMenu.addSeparator();

        dialog.setBounds(400, 200, 350, 150);
        dialog.setLayout(new FlowLayout());

        label.setVisible(false);
        dialog.add(label);

        dialog.setVisible(false);

        myEvent();
    }

    /**
     * 事件
     */
    private static void myEvent() {
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("当前系统版本：" + getValue("config.properties", "version"));
                label.setVisible(true);
                dialog.setVisible(true);
            }
        });
        backupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File targetFile = chooseExportFile("压缩文件", "zip");
                    if (targetFile == null) return;
                    dbService.backupDB(targetFile.getPath());
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        recoveryItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File srcFile = chooseImportFile("压缩文件", "zip");
                    if (srcFile == null) return;
                    dbService.recovery(srcFile.getPath());
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
    }
}
