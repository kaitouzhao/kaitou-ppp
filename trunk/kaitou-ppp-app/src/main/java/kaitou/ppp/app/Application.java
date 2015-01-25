package kaitou.ppp.app;

import com.womai.bsp.tool.utils.PropertyUtil;
import kaitou.ppp.service.DbService;
import kaitou.ppp.service.EngineerService;
import kaitou.ppp.service.ShopService;
import kaitou.ppp.service.SystemSettingsService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * 应用程序入口.
 * User: 赵立伟
 * Date: 2015/1/19
 * Time: 10:43
 */
public class Application {

    private static Log log = LogFactory.getLog(Application.class);

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
    private static Frame mainFrame;
    private static JDialog dialog;
    private static JTextField textField;
    private static JButton confirmBtn;

    /**
     * 主程序入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        initApplicationContext();
        updateSystemSettings();
        backupDB();
        initFrame();
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
        mainFrame = new Frame("PPP-ERP");
        mainFrame.setBounds(300, 100, 600, 500);
        mainFrame.setLayout(new FlowLayout());

        importEngineersBtn = new JButton("导入工程师基本信息");
        mainFrame.add(importEngineersBtn);

        importTrainingsBtn = new JButton("导入工程师发展信息");
        mainFrame.add(importTrainingsBtn);

        importShopsBtn = new JButton("导入认定店基本信息");
        mainFrame.add(importShopsBtn);

        importShopDetailsBtn = new JButton("导入认定店发展信息");
        mainFrame.add(importShopDetailsBtn);

        exportEngineersBtn = new JButton("导出全部工程师基本信息");
        mainFrame.add(exportEngineersBtn);

        exportTrainingsBtn = new JButton("导出全部工程师发展信息");
        mainFrame.add(exportTrainingsBtn);

        exportShopsBtn = new JButton("导出全部认定店基本信息");
        mainFrame.add(exportShopsBtn);

        exportShopDetailsBtn = new JButton("导出认定店发展信息");
        mainFrame.add(exportShopDetailsBtn);

        dialog = new JDialog(mainFrame, "操作提示", true);
        dialog.setBounds(400, 200, 350, 150);
        dialog.setLayout(new FlowLayout());
        dialog.setVisible(false);

        textField = new JTextField(10);
        dialog.add(textField);
        textField.setVisible(false);

        confirmBtn = new JButton("确定");
        dialog.add(confirmBtn);

        myEvent();

        mainFrame.setVisible(true);
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
                    logFail(e1);
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
                    logFail(e1);
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
                    logFail(e1);
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
                    logFail(e1);
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
                    logFail(e1);
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
                    logFail(e1);
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
                    logFail(e1);
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
                    logFail(e1);
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
     * 记录异常
     *
     * @param e 异常
     */
    private static void logFail(Exception e) {
        PrintStream ps = null;
        try {
            String fileName = PropertyUtil.getValue("config.properties", "fail.log");
            ps = new PrintStream(fileName);
            e.printStackTrace(ps);
            ps.flush();
        } catch (FileNotFoundException e2) {
            throw new RuntimeException(e2);
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
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
        log.info("导出文件：" + exportFilePath);
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
        log.info("导入文件：" + importFilePath);
        File srcFile = new File(importFilePath);
        return srcFile;
    }
}
