/*
 * Created by JFormDesigner on Thu Feb 05 10:56:52 CST 2015
 */

package kaitou.ppp.app.ui;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.dialog.InputHint;
import kaitou.ppp.app.ui.dialog.OnlineConfig;
import kaitou.ppp.app.ui.dialog.OperationHint;
import kaitou.ppp.app.ui.table.QueryFrame;
import kaitou.ppp.app.ui.table.queryobject.*;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.domain.shop.ShopRTS;
import kaitou.ppp.domain.system.DBVersion;
import kaitou.ppp.rmi.ServiceClient;
import kaitou.ppp.rmi.service.RemoteDBVersionService;
import kaitou.ppp.rmi.service.RemoteRegistryService;
import kaitou.ppp.service.LocalDBVersionService;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static com.womai.bsp.tool.utils.PropertyUtil.getValue;
import static kaitou.ppp.app.SpringContextManager.*;
import static kaitou.ppp.app.ui.UIUtil.*;

/**
 * 主界面
 *
 * @author 赵立伟
 */
public class MainFrame extends JFrame {

    private static final String FRAME_TITLE = "PPP-ERP主界面";
    private static final int UPGRADE_DB_VERSIONS_WAIT_TIME = 3000;
    private static boolean isOnline = false;
    private static boolean syncOk = false;

    /**
     * 联机状态
     */
    private static enum OnlineStatus {
        ONLINE_PREPARING_FLAG(0, "（联机中......）"), ONLINE_FLAG(1, "（已联机）"), OFFLINE_FLAG(-1, "（未联机）");
        private int key;
        private String displayValue;

        OnlineStatus(int key, String displayValue) {
            this.key = key;
            this.displayValue = displayValue;
        }

        public int getKey() {
            return key;
        }
    }

    /**
     * 启动入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        getUpgradeService().upgradeTo2Dot1();

        asynchronousInit();

        new MainFrame();
    }

    /**
     * 异步初始化
     * <ul>
     * <li>更新系统设置</li>
     * <li>备份DB</li>
     * <li>缓存认定店</li>
     * </ul>
     */
    private static void asynchronousInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getSystemSettingsService().updateSystemSettings();
                getDbService().backupDB();
                getShopService().cacheAllShops();
            }
        }).start();
    }

    /**
     * 异步联机
     * <ul>
     * <li>如果本机IP未设置，则不予联机</li>
     * <li>如果本机是主机，则启动服务，等待注册</li>
     * <li>如果本机非主机，则启动服务，注册</li>
     * </ul>
     */
    private void asynchronousOnline() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String localIp = getSystemSettingsService().getLocalIp();
                    List<String> remoteRegistryIps = getLocalRegistryService().queryRegistryIps();
                    if (StringUtils.isEmpty(localIp) || CollectionUtil.isEmpty(remoteRegistryIps)) {
                        setTitleByOnlineStatus(OnlineStatus.OFFLINE_FLAG);
                        return;
                    }
                    if (!getServiceProvider().start(localIp)) {
                        setTitleByOnlineStatus(OnlineStatus.OFFLINE_FLAG);
                        return;
                    }
                    logOp("已启动服务监听");
                    setTitleByOnlineStatus(OnlineStatus.ONLINE_FLAG);
                    String hostIp = remoteRegistryIps.get(0);
                    if (localIp.equals(hostIp)) {
                        return;
                    }
                    RemoteRegistryService remoteRegistryService = ServiceClient.getRemoteService(RemoteRegistryService.class, hostIp);
                    if (remoteRegistryService == null) {
                        return;
                    }
                    List<String> registryIpsFromHost = remoteRegistryService.register(localIp);
                    logOp("注册成功，已注册IP列表：" + CollectionUtil.list2Str(registryIpsFromHost, ","));
                    getLocalRegistryService().updateRegistryIps(registryIpsFromHost);
                } catch (RemoteException e) {
                    handleEx(e);
                }
            }
        }).start();
    }

    /**
     * 根据联机状态变更窗体标题
     *
     * @param onlineStatus 联机状态
     */
    private void setTitleByOnlineStatus(OnlineStatus onlineStatus) {
        if (OnlineStatus.ONLINE_FLAG == onlineStatus) {
            isOnline = true;
        }
        setTitle(FRAME_TITLE + onlineStatus.displayValue);
        setVisible(true);
    }

    public MainFrame() {
        asynchronousOnline();

        initComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setTitleByOnlineStatus(OnlineStatus.ONLINE_PREPARING_FLAG);

        asynchronousUpgradeDBVersions();
    }

    /**
     * 异步同步DB版本库
     */
    private void asynchronousUpgradeDBVersions() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(UPGRADE_DB_VERSIONS_WAIT_TIME);
                } catch (InterruptedException e) {
                    logSystemEx(e);
                }
                LocalDBVersionService localDBVersionService = getLocalDBVersionService();
                localDBVersionService.cacheDBLatestVersion();
                logOp("已缓存本地DB版本库");
                List<RemoteDBVersionService> remoteDBVersionServices = ServiceClient.queryServicesOfListener(RemoteDBVersionService.class, getLocalRegistryService().queryRegistryIps(), getSystemSettingsService().getLocalIp());
                if (CollectionUtil.isNotEmpty(remoteDBVersionServices)) {
                    try {
                        for (RemoteDBVersionService remoteDBVersionService : remoteDBVersionServices) {
                            List<DBVersion> remoteDBVersions = remoteDBVersionService.queryRemoteDBVersions();
                            if (CollectionUtil.isEmpty(remoteDBVersions)) {
                                continue;
                            }
                            List<DBVersion> toUpgradeList = localDBVersionService.getToUpgradeList(remoteDBVersions);
                            if (CollectionUtil.isEmpty(toUpgradeList)) {
                                continue;
                            }
                            Map<DBVersion, List<String>> toUpgradeDBMap = remoteDBVersionService.queryRemoteDBs(toUpgradeList);
                            if (CollectionUtil.isEmpty(toUpgradeDBMap)) {
                                continue;
                            }
                            localDBVersionService.upgradeByRemoteDBs(toUpgradeDBMap);
                        }
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
                syncOk = true;
            }
        }).start();
    }

    private void importShopBasicActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importShops(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportShopBasicActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportShops(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopBasicActionPerformed(ActionEvent e) {
        new QueryFrame<Shop>(getShopService().queryAllShops(), new ShopQueryObject());
    }

    private void queryEngineerBasicActionPerformed(ActionEvent e) {
        new QueryFrame<Engineer>(getEngineerService().queryAllEngineers(), new EngineerQueryObject());
    }

    private void aboutItemActionPerformed(ActionEvent e) {
        new OperationHint(this, "当前系统版本：" + getValue("config.properties", "version"));
    }

    private void backupDBActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("压缩文件", "zip");
            if (targetFile == null) return;
            getDbService().backupDB(targetFile.getPath());
            new OperationHint(this, "备份成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void recoveryDBActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("压缩文件", "zip");
            if (srcFile == null) return;
            getDbService().recovery(srcFile.getPath());
            new OperationHint(this, "恢复成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importEngineerBasicActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getEngineerService().importEngineers(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportEngineerBasicActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().exportEngineers(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importEngineerTrainingActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getEngineerService().importEngineerTrainings(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryEngineerTrainingActionPerformed(ActionEvent e) {
        new QueryFrame<EngineerTraining>(getEngineerService().queryAllTrainings(), new EngineerTrainingQueryObject());
    }

    private void exportEngineerTrainingActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().exportTrainings(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void countEngineerByProductLineActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().countEngineersByProductLine(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void countEngineerByShopActionPerformed(ActionEvent e) {
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

    private void importShopDetailActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importShopDetails(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopDetailActionPerformed(ActionEvent e) {
        new QueryFrame<ShopDetail>(getShopService().queryAllDetails(), new ShopDetailQueryObject());
    }

    private void exportShopDetailActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出认定年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getExportService().exportShopDetails(targetFile);
            } else {
                getExportService().exportShopDetails(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importShopRTSActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importRTSs(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopRTSActionPerformed(ActionEvent e) {
        new QueryFrame<ShopRTS>(getShopService().queryAllRTSs(), new ShopRTSQueryObject());
    }

    private void exportShopRTSActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportRTSs(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importShopPayActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importPays(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopPayActionPerformed(ActionEvent e) {
        new QueryFrame<ShopPay>(getShopService().queryAllPays(), new ShopPayQueryObject());
    }

    private void exportShopPayActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportPays(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportAllActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportAll(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void genCardMenuActionPerformed(ActionEvent e) {
        try {
            getCardService().generateCards();
            new OperationHint(this, "生成成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void thisMouseClicked(MouseEvent e) {
    }

    private void importCardApplicationRecordActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getCardService().importCardApplicationRecords(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryCardApplicationRecordActionPerformed(ActionEvent e) {
        new QueryFrame<CardApplicationRecord>(getCardService().queryCardApplicationRecords(), new CardApplicationRecordQueryObject());
    }

    private void exportCardApplicationRecordActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getCardService().exportCardApplicationRecords(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void countShopEquipmentActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().countShopEquipment(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void onlineSettingActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"本机ip", "主机ip"});
            if (!inputHint.isOk()) {
                return;
            }
            String localIp = inputHint.getInputResult()[0];
            if (StringUtils.isNotEmpty(localIp)) {
                getSystemSettingsService().updateLocalIp(localIp);
            }
            String hostIp = inputHint.getInputResult()[1];
            if (StringUtils.isNotEmpty(hostIp)) {
                getLocalRegistryService().updateRegistryIps(CollectionUtil.newList(hostIp));
            }
            new OperationHint(this, "设置成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void startOnlineActionPerformed(ActionEvent e) {
        if (isOnline) {
            new OperationHint(this, "已联机");
            return;
        }
        asynchronousOnline();
        setTitleByOnlineStatus(OnlineStatus.ONLINE_PREPARING_FLAG);
        new OperationHint(this, "正在联机中，请稍候......");
    }

    private void onlineConfigActionPerformed(ActionEvent e) {
        new OnlineConfig(this, isOnline, syncOk);
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
        countShopEquipment = new JMenuItem();
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
        cardMenu = new JMenu();
        genCardMenu = new JMenuItem();
        cardApplicationRecordMenu = new JMenu();
        importCardApplicationRecord = new JMenuItem();
        queryCardApplicationRecord = new JMenuItem();
        exportCardApplicationRecord = new JMenuItem();
        onlineMenu = new JMenu();
        onlineSetting = new JMenuItem();
        startOnline = new JMenuItem();
        onlineConfig = new JMenuItem();
        helpMenu = new JMenu();
        aboutItem = new JMenuItem();
        backupDB = new JMenuItem();
        recoveryDB = new JMenuItem();

        //======== this ========
        setTitle("PPP-ERP\u4e3b\u754c\u9762");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                thisMouseClicked(e);
            }
        });
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
                            importShopBasicActionPerformed(e);
                        }
                    });
                    shopBasicMenu.add(importShopBasic);

                    //---- queryShopBasic ----
                    queryShopBasic.setText("\u67e5\u8be2");
                    queryShopBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryShopBasicActionPerformed(e);
                        }
                    });
                    shopBasicMenu.add(queryShopBasic);

                    //---- exportShopBasic ----
                    exportShopBasic.setText("\u5bfc\u51fa");
                    exportShopBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportShopBasicActionPerformed(e);
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
                            importShopDetailActionPerformed(e);
                        }
                    });
                    shopDetailMenu.add(importShopDetail);

                    //---- queryShopDetail ----
                    queryShopDetail.setText("\u67e5\u8be2");
                    queryShopDetail.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryShopDetailActionPerformed(e);
                        }
                    });
                    shopDetailMenu.add(queryShopDetail);

                    //---- exportShopDetail ----
                    exportShopDetail.setText("\u5bfc\u51fa\u5386\u5e74\u8ba4\u5b9a\u5e97\u7ea7\u522b");
                    exportShopDetail.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportShopDetailActionPerformed(e);
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
                            importShopRTSActionPerformed(e);
                        }
                    });
                    rtsMenu.add(importShopRTS);

                    //---- queryShopRTS ----
                    queryShopRTS.setText("\u67e5\u8be2");
                    queryShopRTS.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryShopRTSActionPerformed(e);
                        }
                    });
                    rtsMenu.add(queryShopRTS);

                    //---- exportShopRTS ----
                    exportShopRTS.setText("\u5bfc\u51fa");
                    exportShopRTS.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportShopRTSActionPerformed(e);
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
                            importShopPayActionPerformed(e);
                        }
                    });
                    shopPayMenu.add(importShopPay);

                    //---- queryShopPay ----
                    queryShopPay.setText("\u67e5\u8be2");
                    queryShopPay.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryShopPayActionPerformed(e);
                        }
                    });
                    shopPayMenu.add(queryShopPay);

                    //---- exportShopPay ----
                    exportShopPay.setText("\u5bfc\u51fa");
                    exportShopPay.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportShopPayActionPerformed(e);
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
                        exportAllActionPerformed(e);
                    }
                });
                shopMenu.add(exportAll);

                //---- countShopEquipment ----
                countShopEquipment.setText("\u8ba4\u5b9a\u5e97\u8bbe\u5907\u6570\u5bfc\u51fa");
                countShopEquipment.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        countShopEquipmentActionPerformed(e);
                    }
                });
                shopMenu.add(countShopEquipment);
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
                            importEngineerBasicActionPerformed(e);
                        }
                    });
                    engineerBasicMenu.add(importEngineerBasic);

                    //---- queryEngineerBasic ----
                    queryEngineerBasic.setText("\u67e5\u8be2");
                    queryEngineerBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryEngineerBasicActionPerformed(e);
                        }
                    });
                    engineerBasicMenu.add(queryEngineerBasic);

                    //---- exportEngineerBasic ----
                    exportEngineerBasic.setText("\u5bfc\u51fa");
                    exportEngineerBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportEngineerBasicActionPerformed(e);
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
                            importEngineerTrainingActionPerformed(e);
                        }
                    });
                    engineerTrainingMenu.add(importEngineerTraining);

                    //---- queryEngineerTraining ----
                    queryEngineerTraining.setText("\u67e5\u8be2");
                    queryEngineerTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryEngineerTrainingActionPerformed(e);
                        }
                    });
                    engineerTrainingMenu.add(queryEngineerTraining);

                    //---- exportEngineerTraining ----
                    exportEngineerTraining.setText("\u5bfc\u51fa");
                    exportEngineerTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportEngineerTrainingActionPerformed(e);
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
                        countEngineerByProductLineActionPerformed(e);
                    }
                });
                engineerMenu.add(countEngineerByProductLine);

                //---- countEngineerByShop ----
                countEngineerByShop.setText("\u8ba4\u5b9a\u5e97\u5728\u804c\u4eba\u6570");
                countEngineerByShop.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        countEngineerByShopActionPerformed(e);
                    }
                });
                engineerMenu.add(countEngineerByShop);
            }
            managerMenuBar.add(engineerMenu);

            //======== cardMenu ========
            {
                cardMenu.setText("\u4fdd\u4fee\u5361\u7ba1\u7406");

                //---- genCardMenu ----
                genCardMenu.setText("\u751f\u6210\u4fdd\u4fee\u5361");
                genCardMenu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        genCardMenuActionPerformed(e);
                    }
                });
                cardMenu.add(genCardMenu);

                //======== cardApplicationRecordMenu ========
                {
                    cardApplicationRecordMenu.setText("\u4fdd\u4fee\u5361\u751f\u6210\u8bb0\u5f55");

                    //---- importCardApplicationRecord ----
                    importCardApplicationRecord.setText("\u5bfc\u5165");
                    importCardApplicationRecord.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importCardApplicationRecordActionPerformed(e);
                        }
                    });
                    cardApplicationRecordMenu.add(importCardApplicationRecord);

                    //---- queryCardApplicationRecord ----
                    queryCardApplicationRecord.setText("\u67e5\u8be2");
                    queryCardApplicationRecord.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryCardApplicationRecordActionPerformed(e);
                        }
                    });
                    cardApplicationRecordMenu.add(queryCardApplicationRecord);

                    //---- exportCardApplicationRecord ----
                    exportCardApplicationRecord.setText("\u5bfc\u51fa");
                    exportCardApplicationRecord.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportCardApplicationRecordActionPerformed(e);
                        }
                    });
                    cardApplicationRecordMenu.add(exportCardApplicationRecord);
                }
                cardMenu.add(cardApplicationRecordMenu);
            }
            managerMenuBar.add(cardMenu);

            //======== onlineMenu ========
            {
                onlineMenu.setText("\u8054\u673a");

                //---- onlineSetting ----
                onlineSetting.setText("\u8bbe\u7f6e");
                onlineSetting.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onlineSettingActionPerformed(e);
                    }
                });
                onlineMenu.add(onlineSetting);

                //---- startOnline ----
                startOnline.setText("\u542f\u52a8");
                startOnline.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startOnlineActionPerformed(e);
                    }
                });
                onlineMenu.add(startOnline);

                //---- onlineConfig ----
                onlineConfig.setText("\u914d\u7f6e");
                onlineConfig.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onlineConfigActionPerformed(e);
                    }
                });
                onlineMenu.add(onlineConfig);
            }
            managerMenuBar.add(onlineMenu);

            //======== helpMenu ========
            {
                helpMenu.setText("\u5e2e\u52a9");

                //---- aboutItem ----
                aboutItem.setText("\u5173\u4e8e");
                aboutItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        aboutItemActionPerformed(e);
                    }
                });
                helpMenu.add(aboutItem);

                //---- backupDB ----
                backupDB.setText("\u5907\u4efd\u6570\u636e");
                backupDB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        backupDBActionPerformed(e);
                    }
                });
                helpMenu.add(backupDB);

                //---- recoveryDB ----
                recoveryDB.setText("\u6062\u590d\u6570\u636e");
                recoveryDB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        recoveryDBActionPerformed(e);
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
    private JMenuItem countShopEquipment;
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
    private JMenu cardMenu;
    private JMenuItem genCardMenu;
    private JMenu cardApplicationRecordMenu;
    private JMenuItem importCardApplicationRecord;
    private JMenuItem queryCardApplicationRecord;
    private JMenuItem exportCardApplicationRecord;
    private JMenu onlineMenu;
    private JMenuItem onlineSetting;
    private JMenuItem startOnline;
    private JMenuItem onlineConfig;
    private JMenu helpMenu;
    private JMenuItem aboutItem;
    private JMenuItem backupDB;
    private JMenuItem recoveryDB;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
