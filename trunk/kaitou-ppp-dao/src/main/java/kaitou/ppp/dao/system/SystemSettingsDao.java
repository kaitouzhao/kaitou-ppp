package kaitou.ppp.dao.system;

/**
 * 系统设置DAO.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 11:24
 */
public interface SystemSettingsDao {
    /**
     * 更新上次登录时间
     *
     * @param isTransactionOpen 事务是否开启
     */
    public void updateLastLoginTime(boolean isTransactionOpen);

    /**
     * 更新上次数据备份时间
     */
    public void updateLastBackupTime();

    /**
     * 更新上次数据恢复时间
     */
    public void updateLastRecoveryTime();

    /**
     * 更新最新版本号
     *
     * @param isTransactionOpen 事务是否开启
     */
    public void updateLatestVersion(boolean isTransactionOpen);

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
     * 是否需要备份数据
     *
     * @return 是为真
     */
    public boolean shouldBackup();

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
