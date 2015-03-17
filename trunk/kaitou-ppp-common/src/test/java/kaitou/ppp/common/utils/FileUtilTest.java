package kaitou.ppp.common.utils;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.mock.AMock;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.JsonUtil.json2Object;
import static com.womai.bsp.tool.utils.JsonUtil.object2Json;
import static kaitou.ppp.common.utils.FileUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 文件操作工具类单元测试.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 12:33
 */
public class FileUtilTest {

    private static final String FILE_PATH = "D:\\temp\\ppp\\test\\1.kdb";
    private static final String NAME = "测试3";
    private static final String ID = "03";

    @Test
    public void testWriteLines() throws IOException {
        List<String> lines = new ArrayList<String>();
        lines.add(object2Json(new AMock().setName("测试1").setId("01")));
        lines.add(object2Json(new AMock().setName("测试2").setId("02")));
        writeLines(FILE_PATH, lines);
        lines.clear();
        lines.add(object2Json(new AMock().setName(NAME).setId(ID)));
        lines.add(object2Json(new AMock().setName("测试4").setId("04")));
        writeLines(FILE_PATH, lines);
        testReadLines();
    }

    private void testReadLines() throws IOException {
        List<String> lines = readLines(FILE_PATH);
        assertFalse(CollectionUtil.isEmpty(lines));
        AMock aMock3 = json2Object(lines.get(2), AMock.class);
        assertEquals(NAME, aMock3.getName());
        assertEquals(ID, aMock3.getId());
    }

    @After
    public void testDelete() throws Exception {
        delete(FILE_PATH);
    }
}
