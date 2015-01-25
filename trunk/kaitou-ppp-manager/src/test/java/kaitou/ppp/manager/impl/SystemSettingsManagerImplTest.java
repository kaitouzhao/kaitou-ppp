package kaitou.ppp.manager.impl;

import kaitou.ppp.manager.system.SystemSettingsManager;
import org.junit.Test;

/**
 * 系统设置事务管理层实现单元测试.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 14:26
 */
public class SystemSettingsManagerImplTest extends AbstractManagerTest {

    private SystemSettingsManager mockSystemSettingsManager;

    @Override
    public String getDbDir() {
        return mockSystemSettingsManager.getDbDir();
    }

    @Override
    public void initManager() {
        mockSystemSettingsManager = ctx.getBean(SystemSettingsManager.class);
    }

    @Test
    public void testUpdateSystemSettings() {
        mockSystemSettingsManager.updateSystemSettings();
    }
}
