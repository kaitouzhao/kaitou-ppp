package kaitou.ppp.domain.system;

import kaitou.ppp.domain.BaseDomain;

/**
 * 系统设置.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 11:20
 */
public class SystemSettings extends BaseDomain {
    /**
     * 上次登录时间
     */
    private String lastLoginTime;
    /**
     * 上次数据备份时间
     */
    private String lastBackupTime;
    /**
     * 上次数据恢复时间
     */
    private String lastRecoveryTime;
    /**
     * 最新版本号
     */
    private String latestVersion;
    /**
     * 上次文件选择路径
     */
    private String lastFileChooserPath;
    /**
     * 本机ip
     */
    private String localIp = "";

    @Override
    public String dbFileSuffix() {
        return dbFileName();
    }

    @Override
    public String dbFileName() {
        return getClass().getSimpleName() + CONFIG_SUFFIX;
    }

    @Override
    public void check() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemSettings that = (SystemSettings) o;

        if (lastBackupTime != null ? !lastBackupTime.equals(that.lastBackupTime) : that.lastBackupTime != null)
            return false;
        if (lastFileChooserPath != null ? !lastFileChooserPath.equals(that.lastFileChooserPath) : that.lastFileChooserPath != null)
            return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(that.lastLoginTime) : that.lastLoginTime != null)
            return false;
        if (lastRecoveryTime != null ? !lastRecoveryTime.equals(that.lastRecoveryTime) : that.lastRecoveryTime != null)
            return false;
        if (latestVersion != null ? !latestVersion.equals(that.latestVersion) : that.latestVersion != null)
            return false;
        if (localIp != null ? !localIp.equals(that.localIp) : that.localIp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lastLoginTime != null ? lastLoginTime.hashCode() : 0;
        result = 31 * result + (lastBackupTime != null ? lastBackupTime.hashCode() : 0);
        result = 31 * result + (lastRecoveryTime != null ? lastRecoveryTime.hashCode() : 0);
        result = 31 * result + (latestVersion != null ? latestVersion.hashCode() : 0);
        result = 31 * result + (lastFileChooserPath != null ? lastFileChooserPath.hashCode() : 0);
        result = 31 * result + (localIp != null ? localIp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SystemSettings{" +
                "lastLoginTime='" + lastLoginTime + '\'' +
                ", lastBackupTime='" + lastBackupTime + '\'' +
                ", lastRecoveryTime='" + lastRecoveryTime + '\'' +
                ", latestVersion='" + latestVersion + '\'' +
                ", lastFileChooserPath='" + lastFileChooserPath + '\'' +
                ", localIp='" + localIp + '\'' +
                '}';
    }

    public String getLocalIp() {
        return localIp;
    }

    public SystemSettings setLocalIp(String localIp) {
        this.localIp = localIp;
        return this;
    }

    public String getLastFileChooserPath() {
        return lastFileChooserPath;
    }

    public SystemSettings setLastFileChooserPath(String lastFileChooserPath) {
        this.lastFileChooserPath = lastFileChooserPath;
        return this;
    }

    public String getLastRecoveryTime() {
        return lastRecoveryTime;
    }

    public SystemSettings setLastRecoveryTime(String lastRecoveryTime) {
        this.lastRecoveryTime = lastRecoveryTime;
        return this;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public SystemSettings setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public String getLastBackupTime() {
        return lastBackupTime;
    }

    public SystemSettings setLastBackupTime(String lastBackupTime) {
        this.lastBackupTime = lastBackupTime;
        return this;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public SystemSettings setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
        return this;
    }
}
