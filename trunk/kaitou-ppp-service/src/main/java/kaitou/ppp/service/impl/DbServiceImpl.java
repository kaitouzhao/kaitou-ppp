package kaitou.ppp.service.impl;

import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.service.DbService;

import static kaitou.ppp.common.utils.ZipUtil.zip;

/**
 * DB文件业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 15:46
 */
public class DbServiceImpl implements DbService {

    private SystemSettingsManager systemSettingsManager;
    /**
     * DB文件目录
     */
    private String dbDir;
    /**
     * DB备份文件路径
     */
    private String backDbFile;

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public void setBackDbFile(String backDbFile) {
        this.backDbFile = backDbFile;
    }

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    @Override
    public void backupDB() {
        if (!systemSettingsManager.shouldBackup() && FileUtil.isExists(backDbFile)) {
            return;
        }
        zip(dbDir, backDbFile);
        systemSettingsManager.updateLastBackupTime();
    }
}
