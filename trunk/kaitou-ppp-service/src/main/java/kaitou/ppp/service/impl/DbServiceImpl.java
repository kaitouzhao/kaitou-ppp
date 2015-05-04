package kaitou.ppp.service.impl;

import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.service.DbService;

import java.io.File;
import java.io.IOException;

import static kaitou.ppp.common.utils.FileUtil.delete;
import static kaitou.ppp.common.utils.FileUtil.isExists;
import static kaitou.ppp.common.utils.ZipUtil.unzip;
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
    /**
     * ppp文件目录
     */
    private String pppDir;

    public void setPppDir(String pppDir) {
        this.pppDir = pppDir;
    }

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
        if (!systemSettingsManager.shouldBackup() && isExists(backDbFile)) {
            return;
        }
        backupDB(backDbFile);
    }

    @Override
    public void backupDB(String targetFilePath) {
        try {
            zip(dbDir, targetFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        systemSettingsManager.updateLastBackupTime();
    }

    @Override
    public void recovery(String srcFilePath) {
        String dbBack4Recovery = pppDir + File.separatorChar + "db_back_recovery.zip";
        delete(dbBack4Recovery);
        backupDB(dbBack4Recovery);
        delete(dbDir);
        unzip(srcFilePath, pppDir);
        systemSettingsManager.updateLastRecoveryTime();
    }
}
