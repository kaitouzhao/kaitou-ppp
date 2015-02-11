package kaitou.ppp.common.utils;

import org.junit.Test;

import static kaitou.ppp.common.utils.JsonValidator.validate;
import static kaitou.ppp.common.utils.JsonValidator.validateFile;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JSON校验器单元测试.
 * User: 赵立伟
 * Date: 2015/2/11
 * Time: 14:53
 */
public class JsonValidatorTest {

    public static final String JSON1 = Thread.currentThread().getContextClassLoader().getResource("1.json").getPath();
    public static final String JSON2 = Thread.currentThread().getContextClassLoader().getResource("2.json").getPath();

    @Test
    public void testValidate() {
        assertTrue(validateFile(JSON1));
        assertFalse(validateFile(JSON2));
        assertFalse(validate("dsa@{:}"));
    }
}
