package com.smalld.archetype.common.util;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * zip压缩
 * @author yichuang.chen
 * @date 2018-01-09
 */
public class ZipUtil {

    // 缓冲区大小
    private static final int BUFFER_SIZE = 2048;

    /**
     * 压缩目录
     *
     * @param srcDir           压缩文件夹路径
     * @param out              压缩文件输出流
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void compressDir(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {

        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }

                }
            }
        }
    }


    /**
     * 替换某个 item,
     *
     * @param zipInputStream  zip文件的zip输入流
     * @param zipOutputStream 输出的zip输出流
     * @param itemName        要替换的 item 名称
     * @param itemInputStream 要替换的 item 的内容输入流
     */
    public static void replaceItem(ZipInputStream zipInputStream,
                                   ZipOutputStream zipOutputStream,
                                   String itemName,
                                   InputStream itemInputStream) {
        if (null == zipInputStream) {
            return;
        }
        if (null == zipOutputStream) {
            return;
        }
        if (null == itemName) {
            return;
        }
        if (null == itemInputStream) {
            return;
        }
        ZipEntry entryIn;
        try {
            while ((entryIn = zipInputStream.getNextEntry()) != null) {
                String entryName = entryIn.getName();
                ZipEntry entryOut = new ZipEntry(entryName);
                // 只使用 name
                zipOutputStream.putNextEntry(entryOut);
                // 缓冲区
                byte[] buf = new byte[8 * 1024];
                int len;

                if (entryName.equals(itemName)) {
                    // 使用替换流
                    while ((len = (itemInputStream.read(buf))) > 0) {
                        zipOutputStream.write(buf, 0, len);
                    }
                } else {
                    // 输出普通Zip流
                    while ((len = (zipInputStream.read(buf))) > 0) {
                        zipOutputStream.write(buf, 0, len);
                    }
                }
                // 关闭此 entry
                zipOutputStream.closeEntry();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //e.printStackTrace();
            close(itemInputStream);
            close(zipInputStream);
            close(zipOutputStream);
        }
    }

    /**
     * 替换ZIP中的 批量 条目
     *
     * @param zipInputStream  输入zip流
     * @param zipOutputStream 写出zip流
     * @param replaceItems    替换列表
     */
    public static void replaceItems(ZipInputStream zipInputStream,
                                    ZipOutputStream zipOutputStream,
                                    Map<String, InputStream> replaceItems) {
        if (null == zipInputStream || null == zipOutputStream) {
            return;
        }
        if (null == replaceItems) {
            replaceItems = new HashMap<>();
        }
        ZipEntry entryIn;
        InputStream targetItemInputStream = null;
        try {
            // 1. 替换
            while ((entryIn = zipInputStream.getNextEntry()) != null) {
                String entryName = entryIn.getName();
                //
                boolean shouldReplaceItem = replaceItems.containsKey(entryName);
                targetItemInputStream = replaceItems.get(entryName);
                //
                if (shouldReplaceItem && null == targetItemInputStream) {
                    continue;// 需要删除的item;
                }
                // 只使用 name
                ZipEntry entryOut = new ZipEntry(entryName);
                zipOutputStream.putNextEntry(entryOut);
                // 缓冲区
                byte[] buf = new byte[8 * 1024];
                int len;
                if (shouldReplaceItem) {
                    // 使用替换流
                    while ((len = (targetItemInputStream.read(buf))) > 0) {
                        zipOutputStream.write(buf, 0, len);
                    }
                } else {
                    // 输出普通Zip流
                    while ((len = (zipInputStream.read(buf))) > 0) {
                        zipOutputStream.write(buf, 0, len);
                    }
                }
                // 关闭此 entry
                zipOutputStream.closeEntry();
            }
            // 2. 添加 items
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(targetItemInputStream);
            close(zipInputStream);
            close(zipOutputStream);
        }
    }

    public static void compress(File outputFile, List<File> items, List<String> fileNames) throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

            if (items != null && items.size() > 0) {
                for (int i = 0, size = items.size(); i < size; i++) {
                    compressFile(items.get(i), zipOutputStream, fileNames != null ? fileNames.get(i) : null);
                }
            }
            // 冲刷输出流
            zipOutputStream.flush();
            // 关闭输出流
            zipOutputStream.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * 描述:压缩文件
     *
     * @param file
     * @param zipOutputStream
     * @param fileName
     * @throws Exception
     */
    private static void compressFile(File file, ZipOutputStream zipOutputStream, String fileName) throws Exception {
        try {
            // 压缩文件
            String entryName = fileName != null ? fileName : file.getName();
            ZipEntry zipEntry = new ZipEntry(entryName);
            zipOutputStream.putNextEntry(zipEntry);

            // 读取文件
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

            int count = 0;
            byte data[] = new byte[BUFFER_SIZE];
            while ((count = bis.read(data, 0, BUFFER_SIZE)) != -1) {
                zipOutputStream.write(data, 0, count);
            }
            bis.close();
            zipOutputStream.closeEntry();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * 包装输入流
     */
    public static ZipInputStream wrapZipInputStream(InputStream inputStream) {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        return zipInputStream;
    }

    /**
     * 包装输出流
     */
    public static ZipOutputStream wrapZipOutputStream(OutputStream outputStream) {
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        return zipOutputStream;
    }

    private static void close(InputStream inputStream) {
        if (null != inputStream) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(OutputStream outputStream) {
        if (null != outputStream) {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}