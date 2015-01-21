package kaitou.ppp.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;

/**
 * 压缩工具类.
 * User: 赵立伟
 * Date: 2015/1/21
 * Time: 9:19
 */
public abstract class ZipUtil {
    /**
     * 压缩
     *
     * @param srcPath        源地址，支持文件或文件夹
     * @param targetFilePath 目标文件地址
     */
    public static void zip(String srcPath, String targetFilePath) {
        if (StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(targetFilePath)) {
            return;
        }
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            return;
        }
        FileOutputStream fos = null;
        CheckedOutputStream cos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(targetFilePath);
            cos = new CheckedOutputStream(fos, new CRC32());
            ZipOutputStream zos = new ZipOutputStream(cos);
            zos.setEncoding("gbk");
            bos = new BufferedOutputStream(zos);
            zos.setMethod(ZipOutputStream.DEFLATED);
            zos.setLevel(Deflater.BEST_SPEED);
            String src = srcPath;
            src = src.replaceAll("\\\\", "/");
            String prefixDir;
            if (srcFile.isFile()) {
                prefixDir = src.substring(0, src.lastIndexOf("/") + 1);
            } else {
                prefixDir = (src.replaceAll("/$", "") + "/");
            }
            //如果不是根目录
            if (prefixDir.indexOf("/") != (prefixDir.length() - 1)) {
                prefixDir = prefixDir.replaceAll("[^/]+/$", "");
            }
            writeRecursive(zos, bos, srcFile, prefixDir);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (cos != null) {
                    cos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 递归压缩
     *
     * @param zos       zip输出流
     * @param bos       字节输出流
     * @param srcFile   源目标
     * @param prefixDir 父级目录
     */
    private static void writeRecursive(ZipOutputStream zos, BufferedOutputStream bos,
                                       File srcFile, String prefixDir) {
        BufferedInputStream bi = null;
        try {
            ZipEntry zipEntry;
            String filePath = srcFile.getAbsolutePath().replaceAll("\\\\", "/").replaceAll(
                    "//", "/");
            if (srcFile.isDirectory()) {
                filePath = filePath.replaceAll("/$", "") + "/";
            }
            String entryName = filePath.replace(prefixDir, "").replaceAll("/$", "");
            if (srcFile.isDirectory()) {
                if (!"".equals(entryName)) {
                    //如果是目录，则需要在写目录后面加上 /
                    zipEntry = new ZipEntry(entryName + "/");
                    zos.putNextEntry(zipEntry);
                }
                File srcFiles[] = srcFile.listFiles();
                for (int i = 0; i < srcFiles.length; i++) {
                    writeRecursive(zos, bos, srcFiles[i], prefixDir);
                }
            } else {
                bi = new BufferedInputStream(new FileInputStream(srcFile));
                //开始写入新的ZIP文件条目并将流定位到条目数据的开始处
                zipEntry = new ZipEntry(entryName);
                zos.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];
                int readCount = bi.read(buffer);
                while (readCount != -1) {
                    bos.write(buffer, 0, readCount);
                    readCount = bi.read(buffer);
                }
                //注，在使用缓冲流写压缩文件时，一个条件完后一定要刷新一把，不
                //然可能有的内容就会存入到后面条目中去了
                bos.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (bi != null) {
                try {
                    bi.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
