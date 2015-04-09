package kaitou.ppp.service;

/**
 * 系统设置业务处理层.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 14:20
 */
public interface SystemSettingsService {
    /**
     * 更新系统设置
     */
    public void updateSystemSettings();

    /**
     * 更新上次文件选择路径
     *
     * @param lastFileChooserPath 上次文件选择路径
     */
    public void updateLastFileChooserPath(String lastFileChooserPath);

    /**
     * 获取系统设置值
     *
     * @param fieldName 属性
     * @return 值
     */
    public String getSystemSetting(String fieldName);

    /**
     * 更新本机ip
     *
     * @param localIp 本机ip
     */
    public void updateLocalIp(String localIp);

    /**
     * 获取本机ip
     *
     * @return 本机ip
     */
    public String getLocalIp();
}
