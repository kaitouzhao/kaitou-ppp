package kaitou.ppp.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 网络工具类.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 14:49
 */
public abstract class NetworkUtil {
    /**
     * 获取本地ip
     *
     * @return ip
     */
    public static String getLocalIp() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("获取本机ip失败", e);
        }
    }
}
