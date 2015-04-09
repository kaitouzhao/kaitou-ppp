package kaitou.ppp.common.utils;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static kaitou.ppp.common.utils.NetworkUtil.getLocalIp;
import static org.junit.Assert.assertFalse;

/**
 * 网络工具类单元测试.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 14:55
 */
public class NetworkUtilTest {
    @Test
    public void testGetLocalIp() throws Exception {
//        System.out.println("本机ip地址：" + getLocalIp());
        assertFalse(StringUtils.isEmpty(getLocalIp()));
    }
}
