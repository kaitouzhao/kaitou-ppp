package kaitou.ppp.domain.system;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

/**
 * DB版本库.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 11:38
 */
public class DBVersion extends BaseDomain {

    private static final String LOCAL_VERSION_PREFIX = "Local";
    /**
     * DB文件名
     */
    protected String dbFileName;
    /**
     * 最新版本号
     */
    protected int latestVersion;
    /**
     * 最新修改时间：年-月-日 时:分:秒
     */
    protected String latestModifyTime;

    /**
     * 版本号+1
     *
     * @return DB版本库
     */
    public DBVersion addVersion() {
        latestVersion++;
        return this;
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(dbFileName)) {
            throw new RuntimeException("DB文件名为空");
        }
    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return LOCAL_VERSION_PREFIX + getClass().getSimpleName() + CONFIG_SUFFIX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBVersion dbVersion = (DBVersion) o;

        if (dbFileName != null ? !dbFileName.equals(dbVersion.dbFileName) : dbVersion.dbFileName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dbFileName != null ? dbFileName.hashCode() : 0;
        return result;
    }

    @Override
    public String toString() {
        return "DBVersion{" +
                "dbFileName='" + dbFileName + '\'' +
                ", latestVersion=" + latestVersion +
                ", latestModifyTime='" + latestModifyTime + '\'' +
                '}';
    }

    public String getDbFileName() {
        return dbFileName;
    }

    public void setDbFileName(String dbFileName) {
        this.dbFileName = dbFileName;
    }

    public int getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(int latestVersion) {
        this.latestVersion = latestVersion;
    }

    public String getLatestModifyTime() {
        return latestModifyTime;
    }

    public void setLatestModifyTime(String latestModifyTime) {
        this.latestModifyTime = latestModifyTime;
    }
}
