package kaitou.ppp.dao.system.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import com.womai.bsp.tool.utils.PropertyUtil;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.system.SystemSettingsDao;
import kaitou.ppp.domain.system.SystemSettings;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.womai.bsp.tool.utils.JsonUtil.json2Object;
import static com.womai.bsp.tool.utils.JsonUtil.object2Json;
import static kaitou.ppp.common.utils.FileUtil.*;

/**
 * 系统设置DAO实现.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 12:44
 */
public class SystemSettingsDaoImpl extends BaseDao<SystemSettings> implements SystemSettingsDao {

    /**
     * 备份间隔时间（单位：小时）
     */
    private static final int BACKUP_INTERVAL = 1;
    /**
     * 配置文件名
     */
    private static final String CONFIG_PROPERTIES = "config.properties";
    /**
     * 版本配置键值
     */
    private static final String VERSION_KEY = "version";

    @Override
    public Class<SystemSettings> getDomainClass() {
        return SystemSettings.class;
    }

    /**
     * 获取当前系统设置
     *
     * @param isTransactionOpen 事务是否开启
     * @return 系统设置
     */
    private SystemSettings get(boolean isTransactionOpen) {
        String dbFilePath = isTransactionOpen && isExists(getBackDbFilePath()) ? getBackDbFilePath() : getDbFilePath();
        List<String> lines;
        try {
            lines = readLines(dbFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (CollectionUtil.isEmpty(lines)) {
            return new SystemSettings();
        }
        return json2Object(lines.get(0), SystemSettings.class);
    }

    /**
     * 生成备份的DB文件路径
     *
     * @return 文件路径
     */
    private String getBackDbFilePath() {
        SystemSettings settings = new SystemSettings();
        return dbDir + File.separatorChar + settings.backDbFileName();
    }

    /**
     * 生成DB文件路径
     *
     * @return 文件路径
     */
    private String getDbFilePath() {
        SystemSettings settings = new SystemSettings();
        return dbDir + File.separatorChar + settings.dbFileName();
    }

    @Override
    public void updateLastLoginTime(boolean isTransactionOpen) {
        saveSettings(get(isTransactionOpen).setLastLoginTime(getNowTimeStr()));
    }

    /**
     * 保存系统设置至DB文件
     *
     * @param settings 系统设置
     */
    private void saveSettings(SystemSettings settings) {
        String dbFilePath = getBackDbFilePath();
        FileUtil.delete(dbFilePath);
        List<String> lines = new ArrayList<String>();
        lines.add(object2Json(settings));
        try {
            writeLines(dbFilePath, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前时间的字符串
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    private String getNowTimeStr() {
        return new DateTime().toString("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public void updateLastBackupTime() {
        saveSettings(get(false).setLastBackupTime(getNowTimeStr()));
    }

    @Override
    public void updateLastRecoveryTime() {
        saveSettings(get(false).setLastRecoveryTime(getNowTimeStr()));
    }

    @Override
    public void updateLatestVersion(boolean isTransactionOpen) {
        SystemSettings settings = get(isTransactionOpen);
        String latestVersion = PropertyUtil.getValue(CONFIG_PROPERTIES, VERSION_KEY);
        if (StringUtils.isEmpty(settings.getLatestVersion())) {
            saveSettings(settings.setLatestVersion(latestVersion));
            return;
        }
        try {
            if (Double.valueOf(latestVersion) <= Double.valueOf(settings.getLatestVersion())) {
                return;
            }
        } catch (NumberFormatException e) {
            logSystemEx(e);
        }
        saveSettings(settings.setLatestVersion(latestVersion));
    }

    @Override
    public void updateLastFileChooserPath(String lastFileChooserPath) {
        saveSettings(get(false).setLastFileChooserPath(lastFileChooserPath));
    }

    @Override
    public String getSystemSetting(String fieldName) {
        SystemSettings systemSettings = get(false);
        if (systemSettings == null) {
            return "";
        }
        try {
            Class<SystemSettings> systemSettingsClass = SystemSettings.class;
            Field field = systemSettingsClass.getDeclaredField(fieldName);
            if (field == null) {
                return "";
            }
            Method method = systemSettingsClass.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
            return String.valueOf(method.invoke(systemSettings));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean shouldBackup() {
        SystemSettings settings = get(false);
        if (StringUtils.isEmpty(settings.getLastBackupTime())) {
            return true;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date lastBackupTime = format.parse(settings.getLastBackupTime());
            DateTime lastBackupDateTime = new DateTime(lastBackupTime.getTime());
            return lastBackupDateTime.plusHours(BACKUP_INTERVAL).isBeforeNow();
        } catch (ParseException e) {
            logSystemEx(e);
            return true;
        }
    }

    @Override
    public void updateLocalIp(String localIp) {
        saveSettings(get(false).setLocalIp(localIp));
    }

    @Override
    public String getLocalIp() {
        SystemSettings settings = get(false);
        return settings.getLocalIp();
    }
}
