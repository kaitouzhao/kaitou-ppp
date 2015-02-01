package kaitou.ppp.app;

import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.service.DbService;
import kaitou.ppp.service.EngineerService;
import kaitou.ppp.service.ShopService;
import kaitou.ppp.service.SystemSettingsService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * 应用程序入口.
 * User: 赵立伟
 * Date: 2015/1/19
 * Time: 10:43
 */
@Deprecated
public class Application extends BaseLogManager {

    private static ApplicationContext ctx;
    private static EngineerService engineerService;
    private static ShopService shopService;
    private static SystemSettingsService systemSettingsService;
    private static DbService dbService;

    private static JButton importEngineersBtn;
    private static JButton importTrainingsBtn;
    private static JButton importShopsBtn;
    private static JButton importShopDetailsBtn;
    private static JButton exportEngineersBtn;
    private static JButton exportTrainingsBtn;
    private static JButton exportShopsBtn;
    private static JButton exportShopDetailsBtn;
    private static JButton deleteEngineerBtn;
    private static JButton deleteShopBtn;

    private static Frame mainFrame;
    private static JProgressBar progressBar;

    private static JDialog dialog;
    private static JTextField textField;
    private static JButton confirmBtn;
    /**
     * 删除工程师提示框
     */
    private static JDialog deleteEngineerBasicDialog;
    private static JLabel label11;
    private static JTextField textField11;
    private static JLabel label12;
    private static JTextField textField12;
    private static JLabel label13;
    private static JTextField textField13;
    private static JLabel label14;
    private static JTextField textField14;
    private static JButton confirmBtn1;
    /**
     * 删除认定店基本信息提示框
     */
    private static JDialog deleteShopBasicDialog;
    private static JLabel label21;
    private static JTextField textField21;
    private static JLabel label22;
    private static JTextField textField22;
    private static JButton confirmBtn2;

    /**
     * 主程序入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        long time1 = new DateTime().getMillis();
        start();
        progressBar.setValue(1);
        logSystemInfo("time1:" + (new DateTime().getMillis() - time1));
        initApplicationContext();
        progressBar.setValue(2);
        logSystemInfo("time2:" + (new DateTime().getMillis() - time1));
        updateSystemSettings();
        progressBar.setValue(3);
        logSystemInfo("time3:" + (new DateTime().getMillis() - time1));
        backupDB();
        progressBar.setValue(4);
        logSystemInfo("time4:" + (new DateTime().getMillis() - time1));
        initFrame();
        progressBar.setValue(5);
        logSystemInfo("time5:" + (new DateTime().getMillis() - time1));
        initDeleteEngineerDialog();
        progressBar.setValue(6);
        logSystemInfo("time6:" + (new DateTime().getMillis() - time1));
        initDeleteShopDialog();
        progressBar.setValue(7);
        logSystemInfo("time7:" + (new DateTime().getMillis() - time1));
        myEvent();
        progressBar.setValue(8);
        logSystemInfo("time8:" + (new DateTime().getMillis() - time1));
        shopService.cacheAllShops();
        progressBar.setValue(9);
        logSystemInfo("time9:" + (new DateTime().getMillis() - time1));
        completeStart();
        logSystemInfo("time10:" + (new DateTime().getMillis() - time1));
    }

    /**
     * 启动界面
     */
    private static void start() {
        progressBar = new JProgressBar(1, 9);
        progressBar.setVisible(true);

        mainFrame = new Frame("正在启动PPP-ERP中...");
        mainFrame.setBounds(300, 100, 600, 500);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.add(progressBar);

        mainFrame.setVisible(true);
    }

    /**
     * 完成界面启动
     */
    private static void completeStart() {
        progressBar.setVisible(false);
        mainFrame.setTitle("PPP-ERP");
        importEngineersBtn.setVisible(true);
        importTrainingsBtn.setVisible(true);
        importShopsBtn.setVisible(true);
        importShopDetailsBtn.setVisible(true);
        exportEngineersBtn.setVisible(true);
        exportTrainingsBtn.setVisible(true);
        exportShopsBtn.setVisible(true);
        exportShopDetailsBtn.setVisible(true);
        deleteEngineerBtn.setVisible(true);
        deleteShopBtn.setVisible(true);
    }

    /**
     * 初始化spring容器加载
     */
    private static void initApplicationContext() {
        ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-service.xml"
                }
        );
        engineerService = ctx.getBean(EngineerService.class);
        shopService = ctx.getBean(ShopService.class);
        systemSettingsService = ctx.getBean(SystemSettingsService.class);
        dbService = ctx.getBean(DbService.class);
    }

    /**
     * 自动更新系统设置
     */
    private static void updateSystemSettings() {
        systemSettingsService.updateSystemSettings();
    }

    /**
     * 自动备份DB文件
     */
    private static void backupDB() {
        dbService.backupDB();
    }

    /**
     * 初始化界面
     */
    private static void initFrame() {
        importEngineersBtn = new JButton("导入工程师基本信息");
        importEngineersBtn.setVisible(false);
        mainFrame.add(importEngineersBtn);

        importTrainingsBtn = new JButton("导入工程师发展信息");
        importTrainingsBtn.setVisible(false);
        mainFrame.add(importTrainingsBtn);

        importShopsBtn = new JButton("导入认定店基本信息");
        importShopsBtn.setVisible(false);
        mainFrame.add(importShopsBtn);

        importShopDetailsBtn = new JButton("导入认定店发展信息");
        importShopDetailsBtn.setVisible(false);
        mainFrame.add(importShopDetailsBtn);

        exportEngineersBtn = new JButton("导出全部工程师基本信息");
        exportEngineersBtn.setVisible(false);
        mainFrame.add(exportEngineersBtn);

        exportTrainingsBtn = new JButton("导出全部工程师发展信息");
        exportTrainingsBtn.setVisible(false);
        mainFrame.add(exportTrainingsBtn);

        exportShopsBtn = new JButton("导出全部认定店基本信息");
        exportShopsBtn.setVisible(false);
        mainFrame.add(exportShopsBtn);

        exportShopDetailsBtn = new JButton("导出认定店发展信息");
        exportShopDetailsBtn.setVisible(false);
        mainFrame.add(exportShopDetailsBtn);

        deleteEngineerBtn = new JButton("删除工程师");
        deleteEngineerBtn.setVisible(false);
        mainFrame.add(deleteEngineerBtn);

        deleteShopBtn = new JButton("删除认定店");
        deleteShopBtn.setVisible(false);
        mainFrame.add(deleteShopBtn);

        dialog = new JDialog(mainFrame, "操作提示", true);
        dialog.setBounds(400, 200, 350, 150);
        dialog.setLayout(new FlowLayout());
        dialog.setVisible(false);

        textField = new JTextField(10);
        dialog.add(textField);
        textField.setVisible(false);

        confirmBtn = new JButton("确定");
        dialog.add(confirmBtn);
    }

    /**
     * 初始化删除工程师界面
     */
    private static void initDeleteEngineerDialog() {
        deleteEngineerBasicDialog = new JDialog(mainFrame, "删除工程师", true);
        deleteEngineerBasicDialog.setBounds(400, 200, 350, 150);
        deleteEngineerBasicDialog.setLayout(new FlowLayout());
        deleteEngineerBasicDialog.setVisible(false);

        label11 = new JLabel("区域");
        textField11 = new JTextField(10);
        deleteEngineerBasicDialog.add(label11);
        deleteEngineerBasicDialog.add(textField11);

        label12 = new JLabel("认定店编码");
        textField12 = new JTextField(10);
        deleteEngineerBasicDialog.add(label12);
        deleteEngineerBasicDialog.add(textField12);

        label13 = new JLabel("工程师编号");
        textField13 = new JTextField(10);
        deleteEngineerBasicDialog.add(label13);
        deleteEngineerBasicDialog.add(textField13);

        label14 = new JLabel("工程师产品线");
        textField14 = new JTextField(10);
        deleteEngineerBasicDialog.add(label14);
        deleteEngineerBasicDialog.add(textField14);

        confirmBtn1 = new JButton("确定");
        deleteEngineerBasicDialog.add(confirmBtn1);
    }

    /**
     * 初始化删除认定店界面
     */
    private static void initDeleteShopDialog() {
        deleteShopBasicDialog = new JDialog(mainFrame, "删除认定店", true);
        deleteShopBasicDialog.setBounds(400, 200, 350, 150);
        deleteShopBasicDialog.setLayout(new FlowLayout());
        deleteShopBasicDialog.setVisible(false);

        label21 = new JLabel("区域");
        textField21 = new JTextField(10);
        deleteShopBasicDialog.add(label21);
        deleteShopBasicDialog.add(textField21);

        label22 = new JLabel("认定店编码");
        textField22 = new JTextField(10);
        deleteShopBasicDialog.add(label22);
        deleteShopBasicDialog.add(textField22);

        confirmBtn2 = new JButton("确定");
        deleteShopBasicDialog.add(confirmBtn2);
    }

    /**
     * 事件
     */
    private static void myEvent() {
        importEngineersBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File srcFile = chooseImportFile();
                    if (srcFile == null) return;
                    engineerService.importEngineers(srcFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        importTrainingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File srcFile = chooseImportFile();
                    if (srcFile == null) return;
                    engineerService.importEngineerTrainings(srcFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        importShopsBtn.addActionListener(new ActionListener() {
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
        importShopDetailsBtn.addActionListener(new ActionListener() {
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
        exportEngineersBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File targetFile = chooseExportFile();
                    if (targetFile == null) return;
                    engineerService.exportEngineers(targetFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        exportTrainingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File targetFile = chooseExportFile();
                    if (targetFile == null) return;
                    engineerService.exportTrainings(targetFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        exportShopsBtn.addActionListener(new ActionListener() {
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
        exportShopDetailsBtn.addActionListener(new ActionListener() {
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
        deleteEngineerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEngineerBasicDialog.setVisible(true);
            }
        });
        deleteShopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteShopBasicDialog.setVisible(true);
            }
        });
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        confirmBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteEngineerBasicDialog.setVisible(false);
                    String saleRegion = textField11.getText();
                    String shopId = textField12.getText();
                    String id = textField13.getText();
                    String productLine = textField14.getText();
                    engineerService.deleteEngineer(saleRegion, shopId, id, productLine);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        confirmBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteShopBasicDialog.setVisible(false);
                    String saleRegion = textField21.getText();
                    String shopId = textField22.getText();
                    shopService.deleteShop(saleRegion, shopId);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
            }
        });
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * 显示输入框
     *
     * @param hint 操作提示
     */
    private static void showInput(String hint) {
        dialog.setTitle(hint);
        textField.setVisible(true);
        dialog.setVisible(true);
    }

    /**
     * 显示操作提示
     *
     * @param isSuccess 是否成功
     */
    private static void showMessage(boolean isSuccess) {
        textField.setVisible(false);
        if (isSuccess) {
            dialog.setTitle("操作成功");
        } else {
            dialog.setTitle("操作失败!!");
        }
        dialog.setVisible(true);
    }

    /**
     * 选择导出文件
     *
     * @return 文件
     */
    private static File chooseExportFile() {
        JFileChooser chooser = new JFileChooser(getLastChoosePath());
        chooser.setFileFilter(new FileNameExtensionFilter("excel文件", "xlsx"));
        int returnVal = chooser.showOpenDialog(new JPanel());
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        String exportFilePath = chooser.getSelectedFile().getPath() + ".xlsx";
        systemSettingsService.updateLastFileChooserPath(exportFilePath);
        logOperation("导出文件：" + exportFilePath);
        File srcFile = new File(exportFilePath);
        return srcFile;
    }

    /**
     * 获取上一次选择的文件路径
     *
     * @return 文件路径
     */
    private static String getLastChoosePath() {
        return systemSettingsService.getSystemSetting("lastFileChooserPath");
    }

    /**
     * 选择导入文件
     *
     * @return 文件
     */
    private static File chooseImportFile() {
        JFileChooser chooser = new JFileChooser(getLastChoosePath());
        chooser.setFileFilter(new FileNameExtensionFilter("excel文件", "xls", "xlsx"));
        int returnVal = chooser.showOpenDialog(new JPanel());
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        String importFilePath = chooser.getSelectedFile().getPath();
        systemSettingsService.updateLastFileChooserPath(importFilePath);
        logOperation("导入文件：" + importFilePath);
        File srcFile = new File(importFilePath);
        return srcFile;
    }
}
