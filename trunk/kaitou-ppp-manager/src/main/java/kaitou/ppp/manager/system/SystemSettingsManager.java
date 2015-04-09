package kaitou.ppp.manager.system;

/**
 * 系统设置事务管理层.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 14:02
 */
public interface SystemSettingsManager {
    /**
     * 获取db文件目录
     *
     * @return 目录
     */
    public String getDbDir();

    /**
     * 更新系统设置
     */
    public void updateSystemSettings();

    /**
     * 是否该备份DB
     *
     * @return 是为真
     */
    public boolean shouldBackup();

    /**
     * 更新上一次备份时间
     */
    public void updateLastBackupTime();

    /**
     * 更新上次数据恢复时间
     */
    public void updateLastRecoveryTime();

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
