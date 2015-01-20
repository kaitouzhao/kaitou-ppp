package kaitou.ppp.common.utils;

import java.util.Properties;

/**
 * properties管理工具类.
 * User: 赵立伟
 * Date: 2014/4/25
 * Time: 10:31
 */
public abstract class PropertyUtil {

    /**
     * 获取property值
     *
     * @param name 名称
     * @return 值
     */
    public static String getValue(String name) {
        if (name == null || "".equals(name.trim())) {
            return "";
        }
        java.io.InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
            String value = properties.getProperty(name);
            return new String(value.getBytes("ISO-8859-1"), "gbk");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
