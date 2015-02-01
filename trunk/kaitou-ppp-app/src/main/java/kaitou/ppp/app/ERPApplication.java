package kaitou.ppp.app;

import kaitou.ppp.app.menu.HelpMenuManager;
import kaitou.ppp.service.DbService;

import javax.swing.*;
import java.awt.*;

import static kaitou.ppp.app.menu.EngineerMenuManager.engineerMenu;
import static kaitou.ppp.app.menu.HelpMenuManager.*;
import static kaitou.ppp.app.menu.ShopMenuManager.shopMenu;

/**
 * ERP应用入口.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 23:29
 */
public class ERPApplication extends BaseForm {
    private static DbService dbService;

    /**
     * 启动进度条
     */
    private static JProgressBar progressBar;
    /**
     * 启动步数
     */
    private static final int START_STEP = 4;
    /**
     * 启动阶段
     */
    private static int index = 0;
    /**
     * 菜单栏
     */
    private static JMenuBar menuBar;

    static {
        dbService = ctx.getBean(DbService.class);
    }

    /**
     * 启动入口
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        start();
        updateSystemSettings();
        backupDB();
        cacheAllShop();
        loadMenu();
    }

    /**
     * 进度条前进
     */
    private static void progressForward() {
        progressBar.setValue(++index);
    }

    /**
     * 启动界面
     */
    private static void start() {
        progressBar = new JProgressBar(1, START_STEP);
        progressBar.setVisible(true);

        mainFrame = new JFrame("正在启动PPP-ERP中...");
        mainFrame.setBounds(300, 100, 600, 500);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(progressBar);

        hintDialog = new JDialog(mainFrame, "提示", true);
        hintDialog.setBounds(400, 200, 350, 150);
        hintDialog.setLayout(new FlowLayout());
        hintDialog.setVisible(false);

        mainFrame.setVisible(true);

        progressForward();
    }

    /**
     * 自动更新系统设置
     */
    private static void updateSystemSettings() {
        systemSettingsService.updateSystemSettings();
        progressForward();
    }

    /**
     * 自动备份DB文件
     */
    private static void backupDB() {
        dbService.backupDB();
        progressForward();
    }

    /**
     * 缓存全部认定店
     */
    private static void cacheAllShop() {
        shopService.cacheAllShops();
        progressForward();
    }

    /**
     * 加载菜单
     */
    private static void loadMenu() {
        progressBar.setVisible(false);

        menuBar = new JMenuBar();

        menuBar.add(shopMenu);
        menuBar.add(engineerMenu);
        menuBar.add(helpMenu);

        mainFrame.setJMenuBar(menuBar);
        mainFrame.setTitle("PPP-ERP主界面");
        mainFrame.setVisible(true);
    }

}
