package kaitou.ppp.app.menu;

import kaitou.ppp.app.BaseForm;
import kaitou.ppp.app.dialog.InputDialog;
import kaitou.ppp.service.EngineerService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 工程师管理菜单.
 * User: 赵立伟
 * Date: 2015/1/30
 * Time: 16:42
 */
public class EngineerMenuManager extends BaseForm {

    private static EngineerService engineerService;

    /**
     * 工程师管理
     */
    public static JMenu engineerMenu = new JMenu("工程师管理");
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
     * 培训信息
     */
    private static JMenu trainingMenu = new JMenu("培训信息");
    /**
     * 导入培训信息
     */
    private static JMenuItem importTrainingItem = new JMenuItem("导入");
    /**
     * 导出培训信息
     */
    private static JMenuItem exportTrainingItem = new JMenuItem("导出");
    /**
     * 删除
     */
    private static JMenuItem deleteTrainingItem = new JMenuItem("删除");
    /**
     * 删除培训信息对话框
     */
    private static InputDialog deleteTrainingDialog;
    /**
     * 按产品线统计在职工程师数
     */
    private static JMenuItem countEngineerByProductLineItem = new JMenuItem("产品线在职工程师人数");
    /**
     * 按认定店统计在职工程师数
     */
    private static JMenuItem countEngineerByShopItem = new JMenuItem("认定店在职工程师人数");

    static {
        basicMenu.add(importBasicItem);
        basicMenu.addSeparator();
        basicMenu.add(exportBasicItem);
        basicMenu.addSeparator();
        basicMenu.add(deleteBasicItem);
        basicMenu.addSeparator();

        trainingMenu.add(importTrainingItem);
        trainingMenu.addSeparator();
        trainingMenu.add(exportTrainingItem);
        trainingMenu.addSeparator();
        trainingMenu.add(deleteTrainingItem);
        trainingMenu.addSeparator();

        engineerMenu.add(basicMenu);
        engineerMenu.addSeparator();

        engineerMenu.add(trainingMenu);
        engineerMenu.addSeparator();

        engineerMenu.add(countEngineerByProductLineItem);
        engineerMenu.addSeparator();

        engineerMenu.add(countEngineerByShopItem);
        engineerMenu.addSeparator();

        engineerService = ctx.getBean(EngineerService.class);
        myEvent();
    }

    private static final String[] BASIC_FIELDS = new String[]{"区域", "认定店编码", "工程师编号", "工程师产品线"};
    private static final String[] TRAINING_FIELDS = new String[]{"区域", "认定店编码", "工程师编号", "工程师产品线", "培训机型"};

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
                    engineerService.importEngineers(srcFile);
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
                    engineerService.exportEngineers(targetFile);
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
                    engineerService.deleteEngineer(results[0], results[1], results[2], results[3]);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });

        importTrainingItem.addActionListener(new ActionListener() {
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
        exportTrainingItem.addActionListener(new ActionListener() {
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
        deleteTrainingItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (deleteTrainingDialog == null) {
                        deleteTrainingDialog = new InputDialog(mainFrame, "删除需谨慎！", TRAINING_FIELDS);
                    } else {
                        deleteTrainingDialog.show();
                    }
                    if (!deleteTrainingDialog.isOk()) {
                        return;
                    }
                    String[] results = deleteTrainingDialog.getInputResult();
                    engineerService.deleteEngineerTraining(results[0], results[1], results[2], results[3], results[4]);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });

        countEngineerByProductLineItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File targetFile = chooseExportFile();
                    if (targetFile == null) return;
                    engineerService.countEngineersByProductLine(targetFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
        countEngineerByShopItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File targetFile = chooseExportFile();
                    if (targetFile == null) return;
                    engineerService.countEngineersByShop(targetFile);
                    showMessage(true);
                } catch (Exception e1) {
                    logSystemEx(e1);
                    showMessage(false);
                }
            }
        });
    }
}
