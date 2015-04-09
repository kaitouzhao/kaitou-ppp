package kaitou.ppp.domain.system;

import kaitou.ppp.domain.BaseDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * 远程注册表.
 * User: 赵立伟
 * Date: 2015/3/17
 * Time: 18:35
 */
public class RemoteRegistry extends BaseDomain {
    /**
     * 远程注册ip列表
     * <p>
     * 按照保存顺序依次作为主机备选
     * </p>
     */
    private List<String> remoteIps = new ArrayList<String>();

    @Override
    public void check() {

    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return getClass().getSimpleName() + ".kdb";
    }

    @Override
    public String toString() {
        return "RemoteRegistry{" +
                "remoteIps=" + remoteIps +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {

        return 1;
    }

    public List<String> getRemoteIps() {
        return remoteIps;
    }

    /**
     * 增加注册信息
     *
     * @param ip IP地址
     * @return 远程注册表对象
     */
    public RemoteRegistry addRegistry(String ip) {
        if (!remoteIps.contains(ip)) {
            remoteIps.add(ip);
        }
        return this;
    }
}
