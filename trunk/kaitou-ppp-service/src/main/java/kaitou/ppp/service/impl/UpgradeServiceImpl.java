package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.manager.card.CardApplicationRecordManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.service.UpgradeService;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import static kaitou.ppp.common.utils.FileUtil.copy;
import static kaitou.ppp.common.utils.FileUtil.delete;

/**
 * 版本升级操作实现.
 * User: 赵立伟
 * Date: 2015/2/8
 * Time: 0:15
 */
public class UpgradeServiceImpl extends BaseLogManager implements UpgradeService {
    /**
     * DB文件目录
     */
    private String dbDir;
    /**
     * 配置文件目录
     */
    private String confDir;
    private CardApplicationRecordManager cardApplicationRecordManager;
    private SystemSettingsManager systemSettingsManager;

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    public void setCardApplicationRecordManager(CardApplicationRecordManager cardApplicationRecordManager) {
        this.cardApplicationRecordManager = cardApplicationRecordManager;
    }

    public void setConfDir(String confDir) {
        this.confDir = confDir;
    }

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    @Override
    public void upgradeTo1Dot4() {
        File file = new File(dbDir);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(SysCode.SaleRegion.EAST_CHINA.getValue()) || name.startsWith(SysCode.SaleRegion.SOUTH_CHINA.getValue()) || name.startsWith(SysCode.SaleRegion.NORTH_CHINA.getValue()) || name.startsWith(SysCode.SaleRegion.WEST_CHINA.getValue());
            }
        });
        if (CollectionUtil.isEmpty(files)) {
            return;
        }
        for (File dbFile : files) {
            String fileName = dbFile.getName();
            String newFileName = SysCode.SaleRegion.convert2Code(fileName.substring(0, fileName.indexOf('_'))) + fileName.substring(fileName.indexOf('_'), fileName.length());
            String dbFilePath = dbFile.getPath();
            logSystemInfo("原文件：" + dbFilePath);
            String newDbFile = dbFile.getParent() + File.separatorChar + newFileName;
            logSystemInfo("新文件：" + newDbFile);
            copy(dbFilePath, newDbFile);
            delete(dbFilePath);
        }
    }

    @Override
    public void upgradeTo2Dot1() {
        if ("2.1".equals(systemSettingsManager.getSystemSetting(SysCode.LATEST_VERSION_KEY))) {
            return;
        }
        String systemSettingsDB = dbDir + File.separatorChar + "SystemSettings.kdb";
        if (new File(systemSettingsDB).exists()) {
            copy(systemSettingsDB, confDir + File.separatorChar + "SystemSettings.conf");
            delete(systemSettingsDB);
        }
        String remoteRegistryDB = dbDir + File.separatorChar + "RemoteRegistry.kdb";
        if (new File(remoteRegistryDB).exists()) {
            copy(remoteRegistryDB, confDir + File.separatorChar + "RemoteRegistry.conf");
            delete(remoteRegistryDB);
        }
        List<CardApplicationRecord> cardApplicationRecords = cardApplicationRecordManager.query();
        cardApplicationRecordManager.delete(CollectionUtil.toArray(cardApplicationRecords, CardApplicationRecord.class));
        cardApplicationRecordManager.save(cardApplicationRecords);
    }
}
