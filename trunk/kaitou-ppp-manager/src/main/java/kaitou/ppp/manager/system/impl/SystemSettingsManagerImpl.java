package kaitou.ppp.manager.system.impl;

import kaitou.ppp.dao.system.SystemSettingsDao;
import kaitou.ppp.domain.system.SystemSettings;
import kaitou.ppp.manager.FileDaoManager;
import kaitou.ppp.manager.system.SystemSettingsManager;

/**
 * 系统设置事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 14:07
 */
public class SystemSettingsManagerImpl extends FileDaoManager implements SystemSettingsManager {

    private SystemSettingsDao systemSettingsDao;

    public void setSystemSettingsDao(SystemSettingsDao systemSettingsDao) {
        this.systemSettingsDao = systemSettingsDao;
    }

    @Override
    public String getEntityName() {
        return SystemSettings.class.getSimpleName();
    }

    @Override
    public void updateSystemSettings() {
        systemSettingsDao.updateLastLoginTime(isTransactionOpen());
        systemSettingsDao.updateLatestVersion(isTransactionOpen());
    }

    @Override
    public boolean shouldBackup() {
        return systemSettingsDao.shouldBackup();
    }

    @Override
    public void updateLastBackupTime() {
        systemSettingsDao.updateLastBackupTime();
    }

    @Override
    public void updateLastRecoveryTime() {
        systemSettingsDao.updateLastRecoveryTime();
    }

    @Override
    public void updateLastFileChooserPath(String lastFileChooserPath) {
        systemSettingsDao.updateLastFileChooserPath(lastFileChooserPath);
    }

    @Override
    public String getSystemSetting(String fieldName) {
        return systemSettingsDao.getSystemSetting(fieldName);
    }

    @Override
    public void updateLocalIp(String localIp) {
        systemSettingsDao.updateLocalIp(localIp);
    }

    @Override
    public String getLocalIp() {
        return systemSettingsDao.getLocalIp();
    }
}
