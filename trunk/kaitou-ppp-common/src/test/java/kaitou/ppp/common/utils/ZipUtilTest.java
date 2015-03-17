package kaitou.ppp.common.utils;

import org.junit.Test;

import java.io.IOException;

import static kaitou.ppp.common.utils.ZipUtil.unzip;
import static kaitou.ppp.common.utils.ZipUtil.zip;

/**
 * 压缩工具类单元测试.
 * User: 赵立伟
 * Date: 2015/1/21
 * Time: 9:32
 */
public class ZipUtilTest {

    private static final String SRC_FILE_PATH = "D:\\temp\\ppp\\test\\zip\\2.kdb";
    private static final String SRC_DIR_PATH = "D:\\temp\\ppp\\test\\zip\\kdb";
    private static final String TARGET_FILE_PATH = "D:\\temp\\ppp\\test\\zip\\2.zip";
    private static final String TARGET_DIR_PATH = "D:\\temp\\ppp\\test\\zip\\kdb.zip";

    private static final String UNZIP_PATH = "D:\\temp\\ppp\\test\\zip\\unzip";

    @Test
    public void testZip() throws IOException {
        zip(SRC_FILE_PATH, TARGET_FILE_PATH);
        zip(SRC_DIR_PATH, TARGET_DIR_PATH);
        testUnZip();
    }

    private void testUnZip() {
        unzip(TARGET_FILE_PATH, UNZIP_PATH);
        unzip(TARGET_DIR_PATH, UNZIP_PATH);
    }
}
