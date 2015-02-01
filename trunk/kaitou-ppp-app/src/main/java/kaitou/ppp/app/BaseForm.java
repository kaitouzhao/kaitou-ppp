package kaitou.ppp.app;

import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.service.ShopService;
import kaitou.ppp.service.SystemSettingsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * 界面基类.
 * User: 赵立伟
 * Date: 2015/1/30
 * Time: 16:52
 */
public class BaseForm extends BaseLogManager {

    protected static ApplicationContext ctx;
    protected static SystemSettingsService systemSettingsService;
    protected static ShopService shopService;

    /**
     * 主界面
     */
    protected static JFrame mainFrame;
    /**
     * 提示对话框
     */
    protected static JDialog hintDialog;

    static {
        ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-service.xml"
                }
        );
        systemSettingsService = ctx.getBean(SystemSettingsService.class);
        shopService = ctx.getBean(ShopService.class);
    }

    /**
     * 选择导入文件
     *
     * @return 文件
     */
    protected static File chooseImportFile() {
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

    /**
     * 获取上一次选择的文件路径
     *
     * @return 文件路径
     */
    private static String getLastChoosePath() {
        return systemSettingsService.getSystemSetting("lastFileChooserPath");
    }


    /**
     * 选择导出文件
     *
     * @return 文件
     */
    protected static File chooseExportFile() {
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
     * 显示操作提示框
     *
     * @param isSuccess 是否成功
     */
    protected static void showMessage(boolean isSuccess) {
        if (isSuccess) {
            hintDialog.setTitle("恭喜你！操作成功");
        } else {
            hintDialog.setTitle("很抱歉！操作失败!!");
        }
        hintDialog.setVisible(true);
    }

    /**
     * 显示信息提示框
     *
     * @param message 信息
     */
    protected static void showMessage(String message) {
        hintDialog.setTitle(message);
        hintDialog.setVisible(true);
    }
}
