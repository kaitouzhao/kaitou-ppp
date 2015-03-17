package kaitou.ppp.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;
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
    public static void zip(String srcPath, String targetFilePath) throws IOException {
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
            src = src.replaceAll("//", "/");
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
            if (bos != null) {
                bos.close();
            }
            if (cos != null) {
                cos.close();
            }
            if (fos != null) {
                fos.close();
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
                                       File srcFile, String prefixDir) throws IOException {
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
                assert srcFiles != null;
                for (File srcFile1 : srcFiles) {
                    writeRecursive(zos, bos, srcFile1, prefixDir);
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
                bi.close();
            }
        }
    }

    /**
     * 使用 org.apache.tools.zip.ZipFile 解压文件，它与 java 类库中的
     * java.util.zip.ZipFile 使用方式是一新的，只不过多了设置编码方式的
     * 接口。
     * <p/>
     * 注，apache 没有提供 ZipInputStream 类，所以只能使用它提供的ZipFile
     * 来读取压缩文件。
     *
     * @param archive       压缩包路径
     * @param decompressDir 解压路径
     */
    public static void unzip(String archive, String decompressDir) {
        try {
            ZipFile zf = new ZipFile(archive, "GBK");//支持中文
            Enumeration e = zf.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry ze2 = (ZipEntry) e.nextElement();
                String entryName = ze2.getName();
                String path = decompressDir + "/" + entryName;
                if (ze2.isDirectory()) {
//                    System.out.println("正在创建解压目录 - " + entryName);
                    File decompressDirFile = new File(path);
                    if (!decompressDirFile.exists()) {
                        if (!decompressDirFile.mkdirs()) {
                            throw new RuntimeException("创建解压目录：" + path + "失败");
                        }
                    }
                } else {
//                    System.out.println("正在创建解压文件 - " + entryName);
                    String fileDir = path.substring(0, path.lastIndexOf("/"));
                    File fileDirFile = new File(fileDir);
                    if (!fileDirFile.exists()) {
                        if (!fileDirFile.mkdirs()) {
                            throw new RuntimeException("创建目录：" + fileDir + "失败");
                        }
                    }
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                            decompressDir + "/" + entryName));
                    BufferedInputStream bi = new BufferedInputStream(zf.getInputStream(ze2));
                    byte[] readContent = new byte[1024];
                    int readCount = bi.read(readContent);
                    while (readCount != -1) {
                        bos.write(readContent, 0, readCount);
                        readCount = bi.read(readContent);
                    }
                    bos.close();
                }
            }
            zf.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
