package kaitou.ppp.app;

import com.womai.bsp.tool.utils.PropertyUtil;
import kaitou.ppp.service.DbService;
import kaitou.ppp.service.EngineerService;
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
    private static DbService dbService;
    private static Button importEngineersBtn;
    private static Button importTrainingsBtn;
    private static Button exportEngineersBtn;
    private static Button exportTrainingsBtn;
    private static Frame mainFrame;
    private static Dialog dialog;

    /**
     * 主程序入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        initApplicationContext();
        backDbFile();
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
        dbService = ctx.getBean(DbService.class);
    }

    /**
     * 自动备份最新的数据文件
     */
    private static void backDbFile() {
        dbService.backupDbFile();
    }

    /**
     * 初始化界面
     */
    private static void initFrame() {
        mainFrame = new Frame("工程师信息管理界面");
        mainFrame.setBounds(300, 100, 600, 500);
        mainFrame.setLayout(new FlowLayout());

        importEngineersBtn = new Button("import engineers");
        mainFrame.add(importEngineersBtn);

        importTrainingsBtn = new Button("import trainings");
        mainFrame.add(importTrainingsBtn);

        exportEngineersBtn = new Button("export all engineers");
        mainFrame.add(exportEngineersBtn);

        exportTrainingsBtn = new Button("export all trainings");
        mainFrame.add(exportTrainingsBtn);

        dialog = new Dialog(mainFrame, "操作提示", true);
        dialog.setBounds(400, 200, 350, 150);
        dialog.setLayout(new FlowLayout());
        dialog.setVisible(false);

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
     * 显示操作提示
     *
     * @param isSuccess 是否成功
     */
    private static void showMessage(boolean isSuccess) {
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
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("excel文件", "xlsx"));
        int returnVal = chooser.showOpenDialog(new JPanel());
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        String exportFilePath = chooser.getSelectedFile().getPath() + ".xlsx";
        log.info("导出文件：" + exportFilePath);
        File srcFile = new File(exportFilePath);
        return srcFile;
    }

    /**
     * 选择导入文件
     *
     * @return 文件
     */
    private static File chooseImportFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("excel文件", "xls", "xlsx"));
        int returnVal = chooser.showOpenDialog(new JPanel());
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        String importFilePath = chooser.getSelectedFile().getPath();
        log.info("导入文件：" + importFilePath);
        File srcFile = new File(importFilePath);
        return srcFile;
    }
}
