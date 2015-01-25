package kaitou.ppp.service.impl;

import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.service.SystemSettingsService;

/**
 * 系统设置业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 14:21
 */
public class SystemSettingsServiceImpl implements SystemSettingsService {

    private SystemSettingsManager systemSettingsManager;

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    @Override
    public void updateSystemSettings() {
        systemSettingsManager.updateSystemSettings();
    }

    @Override
    public void updateLastFileChooserPath(String lastFileChooserPath) {
        systemSettingsManager.updateLastFileChooserPath(lastFileChooserPath);
    }

    @Override
    public String getSystemSetting(String fieldName) {
        return systemSettingsManager.getSystemSetting(fieldName);
    }
}
