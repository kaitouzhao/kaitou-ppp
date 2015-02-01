package kaitou.ppp.app.menu;

import kaitou.ppp.app.BaseForm;
import kaitou.ppp.app.dialog.InputDialog;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 认定店管理菜单.
 * User: 赵立伟
 * Date: 2015/1/30
 * Time: 16:37
 */
public class ShopMenuManager extends BaseForm {
    /**
     * 认定店管理
     */
    public static JMenu shopMenu = new JMenu("认定店管理");
    /**
     * 基本信息
     */
    private static JMenu basicMenu = new JMenu("基本信息");
    /**
     * 导入基本信息
     */
    private static JMenuItem importBasicItem = new JMenuItem("导入");
    /**
     * 导出基本信息
     */
    private static JMenuItem exportBasicItem = new JMenuItem("导出");
    /**
     * 删除
     */
    private static JMenuItem deleteBasicItem = new JMenuItem("删除");
    /**
     * 删除基本信息对话框
     */
    private static InputDialog deleteBasicDialog;
    /**
     * 认定信息
     */
    private static JMenu detailMenu = new JMenu("认定级别");
    /**
     * 导入认定信息
     */
    private static JMenuItem importDetailItem = new JMenuItem("导入");
    /**
     * 导出认定信息
     */
    private static JMenuItem exportDetailItem = new JMenuItem("导出历年认定店级别");
    /**
     * 删除
     */
    private static JMenuItem deleteDetailItem = new JMenuItem("删除");
    /**
     * 删除认定信息对话框
     */
    private static InputDialog deleteDetailDialog;
    /**
     * RTS信息
     */
    private static JMenu rtsMenu = new JMenu("RTS");
    /**
     * 导入RTS信息
     */
    private static JMenuItem importRTSItem = new JMenuItem("导入");
    /**
     * 导出RTS信息
     */
    private static JMenuItem exportRTSItem = new JMenuItem("导出");
    /**
     * 删除
     */
    private static JMenuItem deleteRTSItem = new JMenuItem("删除");
    /**
     * 删除RTS信息对话框
     */
    private static InputDialog deleteRTSDialog;
    /**
     * 帐号信息
     */
    private static JMenu payMenu = new JMenu("帐号信息");
    /**
     * 导入帐号信息
     */
    private static JMenuItem importPayItem = new JMenuItem("导入");
    /**
     * 导出帐号信息
     */
    private static JMenuItem exportPayItem = new JMenuItem("导出");
    /**
     * 删除
     */
    private static JMenuItem deletePayItem = new JMenuItem("删除");
    /**
     * 删除帐号信息对话框
     */
    private static InputDialog deletePayDialog;
    /**
     * 基础信息+今年的认定级别
     */
    private static JMenuItem exportAllItem = new JMenuItem("基础信息全导出");

    private static JDialog dialog;
    private static JTextField textField;
    private static JButton confirmBtn = new JButton("确定");

    static {
        basicMenu.add(importBasicItem);
        basicMenu.addSeparator();
        basicMenu.add(exportBasicItem);
        basicMenu.addSeparator();
        basicMenu.add(deleteBasicItem);
        basicMenu.addSeparator();

        detailMenu.add(importDetailItem);
        detailMenu.addSeparator();
        detailMenu.add(exportDetailItem);
        detailMenu.addSeparator();
        detailMenu.add(deleteDetailItem);
        detailMenu.addSeparator();

        rtsMenu.add(importRTSItem);
        rtsMenu.addSeparator();
        rtsMenu.add(exportRTSItem);
        rtsMenu.addSeparator();
        rtsMenu.add(deleteRTSItem);
        rtsMenu.addSeparator();

        payMenu.add(importPayItem);
        payMenu.addSeparator();
        payMenu.add(exportPayItem);
        payMenu.addSeparator();
        payMenu.add(deletePayItem);
        payMenu.addSeparator();

        shopMenu.add(basicMenu);
        shopMenu.addSeparator();

        shopMenu.add(detailMenu);
        shopMenu.addSeparator();

        shopMenu.add(rtsMenu);
        shopMenu.addSeparator();

        shopMenu.add(payMenu);
        shopMenu.addSeparator();

        shopMenu.add(exportAllItem);
        shopMenu.addSeparator();

        myEvent();
    }

    private static final String[] BASIC_FIELDS = new String[]{"区域", "认定店编码"};
    private static final String[] DETAIL_FIELDS = new String[]{"认定店编码", "产品线", "认定年份"};
    private static final String[] RTS_FIELDS = new String[]{"认定店编码", "产品线"};
    private static final String[] PAY_FIELDS = new String[]{"认定店编码"};

    /**
     * 事件
     */
    private static void myEvent() {
        importBasicItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File srcFile = chooseImportFile();
                    if (srcFile == null) return;
                    shopService.importShops(srcFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        exportBasicItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File targetFile = chooseExportFile();
                    if (targetFile == null) return;
                    shopService.exportShops(targetFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        deleteBasicItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (deleteBasicDialog == null) {
                        deleteBasicDialog = new InputDialog(mainFrame, "删除需谨慎！", BASIC_FIELDS);
                    } else {
                        deleteBasicDialog.show();
                    }
                    if (!deleteBasicDialog.isOk()) {
                        return;
                    }
                    String[] results = deleteBasicDialog.getInputResult();
                    shopService.deleteShop(results[0], results[1]);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });

        importDetailItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File srcFile = chooseImportFile();
                    if (srcFile == null) return;
                    shopService.importShopDetails(srcFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        exportDetailItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showInput("请输入导出年份（不填默认全部年份）");
                    String numberOfYear = textField.getText();
                    File targetFile = chooseExportFile();
                    if (targetFile == null) return;
                    if (StringUtils.isEmpty(numberOfYear)) {
                        shopService.exportShopDetails(targetFile);
                    } else {
                        shopService.exportShopDetails(targetFile, numberOfYear);
                    }
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        deleteDetailItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (deleteDetailDialog == null) {
                        deleteDetailDialog = new InputDialog(mainFrame, "删除需谨慎！", DETAIL_FIELDS);
                    } else {
                        deleteDetailDialog.show();
                    }
                    if (!deleteDetailDialog.isOk()) {
                        return;
                    }
                    String[] results = deleteDetailDialog.getInputResult();
                    shopService.deleteShopDetail(results[0], results[1], results[2]);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });

        importRTSItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File srcFile = chooseImportFile();
                    if (srcFile == null) return;
                    shopService.importRTSs(srcFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        exportRTSItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File targetFile = chooseExportFile();
                    if (targetFile == null) return;
                    shopService.exportRTSs(targetFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        deleteRTSItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (deleteRTSDialog == null) {
                        deleteRTSDialog = new InputDialog(mainFrame, "删除需谨慎！", RTS_FIELDS);
                    } else {
                        deleteRTSDialog.show();
                    }
                    if (!deleteRTSDialog.isOk()) {
                        return;
                    }
                    String[] results = deleteRTSDialog.getInputResult();
                    shopService.deleteShopRTS(results[0], results[1]);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });

        importPayItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File srcFile = chooseImportFile();
                    if (srcFile == null) return;
                    shopService.importPays(srcFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        exportPayItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File targetFile = chooseExportFile();
                    if (targetFile == null) return;
                    shopService.exportPays(targetFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        deletePayItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (deletePayDialog == null) {
                        deletePayDialog = new InputDialog(mainFrame, "删除需谨慎！", PAY_FIELDS);
                    } else {
                        deletePayDialog.show();
                    }
                    if (!deletePayDialog.isOk()) {
                        return;
                    }
                    String[] results = deletePayDialog.getInputResult();
                    shopService.deleteShopPay(results[0]);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });

        exportAllItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File targetFile = chooseExportFile();
                    if (targetFile == null) return;
                    shopService.exportAll(targetFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
    }

    /**
     * 显示输入框
     *
     * @param hint 操作提示
     */
    private static void showInput(String hint) {
        dialog = new JDialog(mainFrame, hint, true);
        dialog.setBounds(400, 200, 350, 150);
        dialog.setLayout(new FlowLayout());

        textField = new JTextField(10);
        textField.setVisible(true);
        dialog.add(textField);

        dialog.add(confirmBtn);

        dialog.setVisible(true);
    }

}
