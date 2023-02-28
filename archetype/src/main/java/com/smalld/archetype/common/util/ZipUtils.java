package com.smalld.archetype.common.util;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    /**
     * 压缩成ZIP 方法1
     *
     * @param src 压缩文件夹路径
     * @param out 压缩文件输出流
     */

    public static void toZip(String src, String out) {

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(out))) {
            File file = new File(src);
            compress(file, zos, file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 压缩成ZIP 方法1
     *
     * @param src 压缩文件夹路径
     * @param out 压缩文件输出流
     */

    public static void toZip(String src, OutputStream out) {

        try (ZipOutputStream zos = new ZipOutputStream(out)) {
            File file = new File(src);
            compress(file, zos, file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 压缩成ZIP 方法2
     *
     * @param files 需要压缩的文件列表
     * @param out   压缩文件输出流
     */

    public static void toZip(List<File> files, OutputStream out) {

        try (ZipOutputStream zos = new ZipOutputStream(out)) {
            for (File srcFile : files) {
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                FileInputStream in = new FileInputStream(srcFile);
                copy(in, zos);
                zos.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 递归压缩方法
     *
     * @param file 源文件
     * @param zos  zip输出流
     * @param name 压缩后的名称
     */

    private static void compress(File file, ZipOutputStream zos, String name) throws Exception {
        File temp = file;
        boolean noCopy = name.endsWith("1.xxxx");
        if (file.isFile() && !noCopy) {
            zos.putNextEntry(new ZipEntry(name));
            FileInputStream in = new FileInputStream(temp);
            copy(in, zos);
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                if (noCopy) {
                    zos.putNextEntry(new ZipEntry(name.replace(File.separator + "1.xxxx", "") + File.separator));
                } else {
                    zos.putNextEntry(new ZipEntry(name + File.separator));
                }
                zos.closeEntry();
            } else {
                for (File target : listFiles) {
                    compress(target, zos, name + File.separator + target.getName());
                }
            }
        }
    }

    private static boolean copy(InputStream in, OutputStream out) {

        BufferedInputStream bis = new BufferedInputStream(in);
        byte[] buf = new byte[8192];
        int size;
        try {
            while ((size = bis.read(buf)) != -1) {
                out.write(buf, 0, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

}
