package kaitou.ppp.common.utils;

import com.womai.bsp.tool.utils.CollectionUtil;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 12:22
 */
public abstract class FileUtil {

    /**
     * 向文件插入新行
     *
     * @param filePath 文件路径
     * @param lines    新行
     * @return 成功记录数
     */
    public static int writeLines(String filePath, List<String> lines) throws IOException {
        PrintWriter out = null;
        FileWriter fw = null;
        try {
            File file = new File(filePath);
            if (!createFile(filePath)) {
                throw new RuntimeException("创建文件失败：" + filePath);
            }
            List<String> allLines = new ArrayList<String>();
            allLines.addAll(readLines(filePath));
            allLines.addAll(lines);
            fw = new FileWriter(file);
            out = new PrintWriter(fw);
            int successCount = 0;
            for (String newLine : allLines) {
                out.println(newLine);
                successCount++;
            }
            out.flush();
            return successCount;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (fw != null) {
                fw.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 创建文件
     *
     * @param filePath 文件路径
     * @return 是否成功
     */
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            if (!createDir(parentFile)) {
                return false;
            }
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 递归创建文件目录
     *
     * @param dir 目录
     * @return 是否成功
     */
    private static boolean createDir(File dir) {
        if (dir.exists()) {
            return true;
        }
        File parentDir = dir.getParentFile();
        if (!parentDir.exists()) {
            if (!createDir(parentDir)) {
                return false;
            }
        }
        return dir.mkdir();
    }

    /**
     * 从文件中读取行
     *
     * @param filePath 文件路径
     * @return 行
     */
    public static List<String> readLines(String filePath) throws RuntimeException, IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<String>();
        }
        BufferedReader reader = null;
        FileReader fr = null;
        try {
            List<String> result = new ArrayList<String>();
            fr = new FileReader(file);
            reader = new BufferedReader(fr);
            String oneLine;
            while ((oneLine = reader.readLine()) != null) {
                result.add(oneLine);
            }
            return result;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fr != null) {
                fr.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public static void delete(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            if (!file.delete()) {
                throw new RuntimeException("删除文件：" + filePath + "失败");
            }
            return;
        }
        File[] files = file.listFiles();
        if (!CollectionUtil.isEmpty(files)) {
            assert files != null;
            for (File f : files) {
                delete(f.getAbsolutePath());
            }
        }
        if (!file.delete()) {
            throw new RuntimeException("删除目录：" + filePath + "失败");
        }
    }

    /**
     * 拷贝文件
     *
     * @param srcPath    源文件路径
     * @param targetPath 目标文件路径
     */
    public static void copy(String srcPath, String targetPath) {
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            return;
        }
        delete(targetPath);
        try {
            writeLines(targetPath, readLines(srcPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验文件是否存在
     *
     * @param filePath 文件路径
     * @return 如果文件路径为空或者文件不存在，则为真
     */
    public static boolean isExists(String filePath) {
        return StringUtils.isEmpty(filePath) || new File(filePath).exists();
    }
}
