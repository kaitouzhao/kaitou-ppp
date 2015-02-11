package kaitou.ppp.common.utils;

import kaitou.ppp.common.mock.AMock;
import org.junit.Test;

import static kaitou.ppp.common.utils.ReflectionUtil.newInstance;
import static org.junit.Assert.assertFalse;

/**
 * 反射工具类单元测试.
 * User: 赵立伟
 * Date: 2015/2/10
 * Time: 12:43
 */
public class ReflectionUtilTest {

    @Test
    public void testCreate() {
        AMock obj = newInstance(AMock.class);
        assertFalse(obj == null);
    }
}
