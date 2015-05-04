package kaitou.ppp.domain.system;

import org.apache.commons.lang.StringUtils;

/**
 * DB文件版本待发箱.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 13:57
 */
public class OutboxDBVersion extends DBVersion {
    /**
     * 目标IP
     */
    private String destIp;

    @Override
    public String dbFileSuffix() {
        return getClass().getSimpleName() + DB_SUFFIX;
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(destIp)) {
            throw new RuntimeException("目标IP为空");
        }
        super.check();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutboxDBVersion that = (OutboxDBVersion) o;

        if (destIp != null ? !destIp.equals(that.destIp) : that.destIp != null) return false;
        if (dbFileName != null ? !dbFileName.equals(that.dbFileName) : that.dbFileName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (destIp != null ? destIp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OutboxDBVersion{" +
                "dbFileName='" + dbFileName + '\'' +
                ", latestVersion=" + latestVersion +
                ", latestModifyTime='" + latestModifyTime + '\'' +
                ", destIp='" + destIp + '\'' +
                '}';
    }

    public String getDestIp() {
        return destIp;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }
}
