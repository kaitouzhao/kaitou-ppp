package kaitou.ppp.common.utils;

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
    public static int writeLines(String filePath, List<String> lines) {
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
                try {
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
    private static boolean createFile(String filePath) {
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
    public static List<String> readLines(String filePath) {
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
                try {
                    fr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
        file.delete();
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
        writeLines(targetPath, readLines(srcPath));
    }

}
