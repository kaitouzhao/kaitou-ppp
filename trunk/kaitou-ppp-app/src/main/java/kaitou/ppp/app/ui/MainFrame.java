/*
 * Created by JFormDesigner on Thu Feb 05 10:56:52 CST 2015
 */

package kaitou.ppp.app.ui;

import kaitou.ppp.app.ui.dialog.InputHint;
import kaitou.ppp.app.ui.dialog.OperationHint;
import kaitou.ppp.app.ui.table.QueryFrame;
import kaitou.ppp.app.ui.table.queryobject.*;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.domain.shop.ShopRTS;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static com.womai.bsp.tool.utils.PropertyUtil.getValue;
import static kaitou.ppp.app.SpringContextManager.*;
import static kaitou.ppp.app.ui.UIUtil.*;

/**
 * 主界面
 *
 * @author 赵立伟
 */
public class MainFrame extends JFrame {

    /**
     * 启动入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        getSystemSettingsService().updateSystemSettings();
        getDbService().backupDB();
        getShopService().cacheAllShops();

        new MainFrame();
    }

    public MainFrame() {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void importShopBasicActionPerformed() {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importShops(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportShopBasicActionPerformed() {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportShops(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopBasicActionPerformed() {
        new QueryFrame<Shop>(getShopService().queryAllShops(), new ShopQueryObject());
    }

    private void queryEngineerBasicActionPerformed() {
        new QueryFrame<Engineer>(getEngineerService().queryAllEngineers(), new EngineerQueryObject());
    }

    private void aboutItemActionPerformed() {
        new OperationHint(this, "当前系统版本：" + getValue("config.properties", "version"));
    }

    private void backupDBActionPerformed() {
        try {
            File targetFile = chooseExportFile("压缩文件", "zip");
            if (targetFile == null) return;
            getDbService().backupDB(targetFile.getPath());
            new OperationHint(this, "备份成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void recoveryDBActionPerformed() {
        try {
            File srcFile = chooseImportFile("压缩文件", "zip");
            if (srcFile == null) return;
            getDbService().recovery(srcFile.getPath());
            new OperationHint(this, "恢复成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importEngineerBasicActionPerformed() {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getEngineerService().importEngineers(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportEngineerBasicActionPerformed() {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().exportEngineers(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importEngineerTrainingActionPerformed() {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getEngineerService().importEngineerTrainings(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryEngineerTrainingActionPerformed() {
        new QueryFrame<EngineerTraining>(getEngineerService().queryAllTrainings(), new EngineerTrainingQueryObject());
    }

    private void exportEngineerTrainingActionPerformed() {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().exportTrainings(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void countEngineerByProductLineActionPerformed() {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().countEngineersByProductLine(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void countEngineerByShopActionPerformed() {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"请选择产品线"});
            if (!inputHint.isOk()) {
                return;
            }
            String productLine = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().countEngineersByShop(productLine, targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importShopDetailActionPerformed() {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importShopDetails(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopDetailActionPerformed() {
        new QueryFrame<ShopDetail>(getShopService().queryAllDetails(), new ShopDetailQueryObject());
    }

    private void exportShopDetailActionPerformed() {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出认定年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getShopService().exportShopDetails(targetFile);
            } else {
                getShopService().exportShopDetails(targetFile, numberOfYear);
            }
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importShopRTSActionPerformed() {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importRTSs(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopRTSActionPerformed() {
        new QueryFrame<ShopRTS>(getShopService().queryAllRTSs(), new ShopRTSQueryObject());
    }

    private void exportShopRTSActionPerformed() {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportRTSs(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importShopPayActionPerformed() {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importPays(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopPayActionPerformed() {
        new QueryFrame<ShopPay>(getShopService().queryAllPays(), new ShopPayQueryObject());
    }

    private void exportShopPayActionPerformed() {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportPays(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportAllActionPerformed() {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportAll(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        managerMenuBar = new JMenuBar();
        shopMenu = new JMenu();
        shopBasicMenu = new JMenu();
        importShopBasic = new JMenuItem();
        queryShopBasic = new JMenuItem();
        exportShopBasic = new JMenuItem();
        shopDetailMenu = new JMenu();
        importShopDetail = new JMenuItem();
        queryShopDetail = new JMenuItem();
        exportShopDetail = new JMenuItem();
        rtsMenu = new JMenu();
        importShopRTS = new JMenuItem();
        queryShopRTS = new JMenuItem();
        exportShopRTS = new JMenuItem();
        shopPayMenu = new JMenu();
        importShopPay = new JMenuItem();
        queryShopPay = new JMenuItem();
        exportShopPay = new JMenuItem();
        exportAll = new JMenuItem();
        engineerMenu = new JMenu();
        engineerBasicMenu = new JMenu();
        importEngineerBasic = new JMenuItem();
        queryEngineerBasic = new JMenuItem();
        exportEngineerBasic = new JMenuItem();
        engineerTrainingMenu = new JMenu();
        importEngineerTraining = new JMenuItem();
        queryEngineerTraining = new JMenuItem();
        exportEngineerTraining = new JMenuItem();
        countEngineerByProductLine = new JMenuItem();
        countEngineerByShop = new JMenuItem();
        helpMenu = new JMenu();
        aboutItem = new JMenuItem();
        backupDB = new JMenuItem();
        recoveryDB = new JMenuItem();

        //======== this ========
        setTitle("PPP-ERP\u4e3b\u754c\u9762");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== managerMenuBar ========
        {

            //======== shopMenu ========
            {
                shopMenu.setText("\u8ba4\u5b9a\u5e97\u7ba1\u7406");

                //======== shopBasicMenu ========
                {
                    shopBasicMenu.setText("\u57fa\u672c\u4fe1\u606f");

                    //---- importShopBasic ----
                    importShopBasic.setText("\u5bfc\u5165");
                    importShopBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importShopBasicActionPerformed();
                        }
                    });
                    shopBasicMenu.add(importShopBasic);

                    //---- queryShopBasic ----
                    queryShopBasic.setText("\u67e5\u8be2");
                    queryShopBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryShopBasicActionPerformed();
                        }
                    });
                    shopBasicMenu.add(queryShopBasic);

                    //---- exportShopBasic ----
                    exportShopBasic.setText("\u5bfc\u51fa");
                    exportShopBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportShopBasicActionPerformed();
                        }
                    });
                    shopBasicMenu.add(exportShopBasic);
                }
                shopMenu.add(shopBasicMenu);

                //======== shopDetailMenu ========
                {
                    shopDetailMenu.setText("\u8ba4\u5b9a\u7ea7\u522b");

                    //---- importShopDetail ----
                    importShopDetail.setText("\u5bfc\u5165");
                    importShopDetail.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importShopDetailActionPerformed();
                        }
                    });
                    shopDetailMenu.add(importShopDetail);

                    //---- queryShopDetail ----
                    queryShopDetail.setText("\u67e5\u8be2");
                    queryShopDetail.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryShopDetailActionPerformed();
                        }
                    });
                    shopDetailMenu.add(queryShopDetail);

                    //---- exportShopDetail ----
                    exportShopDetail.setText("\u5bfc\u51fa\u5386\u5e74\u8ba4\u5b9a\u5e97\u7ea7\u522b");
                    exportShopDetail.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportShopDetailActionPerformed();
                        }
                    });
                    shopDetailMenu.add(exportShopDetail);
                }
                shopMenu.add(shopDetailMenu);

                //======== rtsMenu ========
                {
                    rtsMenu.setText("RTS");

                    //---- importShopRTS ----
                    importShopRTS.setText("\u5bfc\u5165");
                    importShopRTS.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importShopRTSActionPerformed();
                        }
                    });
                    rtsMenu.add(importShopRTS);

                    //---- queryShopRTS ----
                    queryShopRTS.setText("\u67e5\u8be2");
                    queryShopRTS.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryShopRTSActionPerformed();
                        }
                    });
                    rtsMenu.add(queryShopRTS);

                    //---- exportShopRTS ----
                    exportShopRTS.setText("\u5bfc\u51fa");
                    exportShopRTS.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportShopRTSActionPerformed();
                        }
                    });
                    rtsMenu.add(exportShopRTS);
                }
                shopMenu.add(rtsMenu);

                //======== shopPayMenu ========
                {
                    shopPayMenu.setText("\u5e10\u53f7\u4fe1\u606f");

                    //---- importShopPay ----
                    importShopPay.setText("\u5bfc\u5165");
                    importShopPay.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importShopPayActionPerformed();
                        }
                    });
                    shopPayMenu.add(importShopPay);

                    //---- queryShopPay ----
                    queryShopPay.setText("\u67e5\u8be2");
                    queryShopPay.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryShopPayActionPerformed();
                        }
                    });
                    shopPayMenu.add(queryShopPay);

                    //---- exportShopPay ----
                    exportShopPay.setText("\u5bfc\u51fa");
                    exportShopPay.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportShopPayActionPerformed();
                        }
                    });
                    shopPayMenu.add(exportShopPay);
                }
                shopMenu.add(shopPayMenu);

                //---- exportAll ----
                exportAll.setText("\u57fa\u7840\u4fe1\u606f\u5168\u5bfc\u51fa");
                exportAll.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        exportAllActionPerformed();
                    }
                });
                shopMenu.add(exportAll);
            }
            managerMenuBar.add(shopMenu);

            //======== engineerMenu ========
            {
                engineerMenu.setText("\u5de5\u7a0b\u5e08\u7ba1\u7406");

                //======== engineerBasicMenu ========
                {
                    engineerBasicMenu.setText("\u57fa\u672c\u4fe1\u606f");

                    //---- importEngineerBasic ----
                    importEngineerBasic.setText("\u5bfc\u5165");
                    importEngineerBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importEngineerBasicActionPerformed();
                        }
                    });
                    engineerBasicMenu.add(importEngineerBasic);

                    //---- queryEngineerBasic ----
                    queryEngineerBasic.setText("\u67e5\u8be2");
                    queryEngineerBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryEngineerBasicActionPerformed();
                        }
                    });
                    engineerBasicMenu.add(queryEngineerBasic);

                    //---- exportEngineerBasic ----
                    exportEngineerBasic.setText("\u5bfc\u51fa");
                    exportEngineerBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportEngineerBasicActionPerformed();
                        }
                    });
                    engineerBasicMenu.add(exportEngineerBasic);
                }
                engineerMenu.add(engineerBasicMenu);

                //======== engineerTrainingMenu ========
                {
                    engineerTrainingMenu.setText("\u57f9\u8bad\u4fe1\u606f");

                    //---- importEngineerTraining ----
                    importEngineerTraining.setText("\u5bfc\u5165");
                    importEngineerTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importEngineerTrainingActionPerformed();
                        }
                    });
                    engineerTrainingMenu.add(importEngineerTraining);

                    //---- queryEngineerTraining ----
                    queryEngineerTraining.setText("\u67e5\u8be2");
                    queryEngineerTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryEngineerTrainingActionPerformed();
                        }
                    });
                    engineerTrainingMenu.add(queryEngineerTraining);

                    //---- exportEngineerTraining ----
                    exportEngineerTraining.setText("\u5bfc\u51fa");
                    exportEngineerTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportEngineerTrainingActionPerformed();
                        }
                    });
                    engineerTrainingMenu.add(exportEngineerTraining);
                }
                engineerMenu.add(engineerTrainingMenu);

                //---- countEngineerByProductLine ----
                countEngineerByProductLine.setText("\u4ea7\u54c1\u7ebf\u5728\u804c\u4eba\u6570");
                countEngineerByProductLine.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        countEngineerByProductLineActionPerformed();
                    }
                });
                engineerMenu.add(countEngineerByProductLine);

                //---- countEngineerByShop ----
                countEngineerByShop.setText("\u8ba4\u5b9a\u5e97\u5728\u804c\u4eba\u6570");
                countEngineerByShop.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        countEngineerByShopActionPerformed();
                    }
                });
                engineerMenu.add(countEngineerByShop);
            }
            managerMenuBar.add(engineerMenu);

            //======== helpMenu ========
            {
                helpMenu.setText("\u5e2e\u52a9");

                //---- aboutItem ----
                aboutItem.setText("\u5173\u4e8e");
                aboutItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        aboutItemActionPerformed();
                    }
                });
                helpMenu.add(aboutItem);

                //---- backupDB ----
                backupDB.setText("\u5907\u4efd\u6570\u636e");
                backupDB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        backupDBActionPerformed();
                    }
                });
                helpMenu.add(backupDB);

                //---- recoveryDB ----
                recoveryDB.setText("\u6062\u590d\u6570\u636e");
                recoveryDB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        recoveryDBActionPerformed();
                    }
                });
                helpMenu.add(recoveryDB);
            }
            managerMenuBar.add(helpMenu);
        }
        setJMenuBar(managerMenuBar);

        contentPane.setPreferredSize(new Dimension(400, 300));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar managerMenuBar;
    private JMenu shopMenu;
    private JMenu shopBasicMenu;
    private JMenuItem importShopBasic;
    private JMenuItem queryShopBasic;
    private JMenuItem exportShopBasic;
    private JMenu shopDetailMenu;
    private JMenuItem importShopDetail;
    private JMenuItem queryShopDetail;
    private JMenuItem exportShopDetail;
    private JMenu rtsMenu;
    private JMenuItem importShopRTS;
    private JMenuItem queryShopRTS;
    private JMenuItem exportShopRTS;
    private JMenu shopPayMenu;
    private JMenuItem importShopPay;
    private JMenuItem queryShopPay;
    private JMenuItem exportShopPay;
    private JMenuItem exportAll;
    private JMenu engineerMenu;
    private JMenu engineerBasicMenu;
    private JMenuItem importEngineerBasic;
    private JMenuItem queryEngineerBasic;
    private JMenuItem exportEngineerBasic;
    private JMenu engineerTrainingMenu;
    private JMenuItem importEngineerTraining;
    private JMenuItem queryEngineerTraining;
    private JMenuItem exportEngineerTraining;
    private JMenuItem countEngineerByProductLine;
    private JMenuItem countEngineerByShop;
    private JMenu helpMenu;
    private JMenuItem aboutItem;
    private JMenuItem backupDB;
    private JMenuItem recoveryDB;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
