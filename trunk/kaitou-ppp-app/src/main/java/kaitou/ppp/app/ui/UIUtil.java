package kaitou.ppp.app.ui;

import kaitou.ppp.app.ui.dialog.OperationHint;
import kaitou.ppp.common.log.BaseLogManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import static kaitou.ppp.app.SpringContextManager.getSystemSettingsService;

/**
 * UI工具类.
 * User: 赵立伟
 * Date: 2015/2/5
 * Time: 15:19
 */
public abstract class UIUtil extends BaseLogManager {

    /**
     * 选择导入文件
     *
     * @param description 文件类型描述
     * @param extensions  文件扩展名
     * @return 文件
     */
    protected static File chooseImportFile(String description, String... extensions) {
        JFileChooser chooser = new JFileChooser(getLastChoosePath());
        chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        int returnVal = chooser.showOpenDialog(new JPanel());
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        String importFilePath = chooser.getSelectedFile().getPath();
        getSystemSettingsService().updateLastFileChooserPath(importFilePath);
        logOperation("导入文件：" + importFilePath);
        return new File(importFilePath);
    }

    /**
     * 获取上一次选择的文件路径
     *
     * @return 文件路径
     */
    private static String getLastChoosePath() {
        return getSystemSettingsService().getSystemSetting("lastFileChooserPath");
    }


    /**
     * 选择导出文件
     *
     * @param description 文件类型描述
     * @param extensions  文件扩展名
     * @return 文件
     */
    public static File chooseExportFile(String description, String... extensions) {
        JFileChooser chooser = new JFileChooser(getLastChoosePath());
        chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        int returnVal = chooser.showOpenDialog(new JPanel());
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        String exportFilePath = chooser.getSelectedFile().getPath() + '.' + extensions[0];
        getSystemSettingsService().updateLastFileChooserPath(exportFilePath);
        logOperation("导出文件：" + exportFilePath);
        return new File(exportFilePath);
    }

    /**
     * 异常处理
     * <p>
     * 会弹出提示框
     * </p>
     *
     * @param ex    异常
     * @param frame 父窗体
     */
    public static void handleEx(Exception ex, JFrame frame) {
        logSystemEx(ex);
        new OperationHint(frame, "很抱歉！出错了~出错原因：" + ex.getMessage());
    }

    /**
     * 异常处理
     *
     * @param ex 异常
     */
    public static void handleEx(Exception ex) {
        logSystemEx(ex);
    }

    /**
     * 操作日志记录
     *
     * @param info 信息
     */
    public static void logOp(String info) {
        logOperation(info);
    }
}
