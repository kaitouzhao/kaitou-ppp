package kaitou.ppp.common.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static kaitou.ppp.common.utils.ZipUtil.zip;

/**
 * 压缩工具类单元测试.
 * User: 赵立伟
 * Date: 2015/1/21
 * Time: 9:32
 */
public class ZipUtilTest {

    private static final String SRC_FILE_PATH = Thread.currentThread().getContextClassLoader().getResource("2.kdb").getPath();
    private static final String SRC_DIR_PATH = Thread.currentThread().getContextClassLoader().getResource("kdb").getPath();
    private static StringBuilder targetFilePath = new StringBuilder("2.zip");
    private static StringBuilder targetDirPath = new StringBuilder("kdb.zip");

    @Before
    public void setUp() {
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("kdb").getPath());
        targetFilePath.insert(0, file.getParent() + File.separatorChar);
        targetDirPath.insert(0, file.getParent() + File.separatorChar);
    }

    @Test
    public void testZip() {
        zip(SRC_FILE_PATH, targetFilePath.toString());
        zip(SRC_DIR_PATH, targetDirPath.toString());
    }
}
