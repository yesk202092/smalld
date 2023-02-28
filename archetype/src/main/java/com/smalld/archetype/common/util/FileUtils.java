package com.smalld.archetype.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件操作
 * @author chenyc01
 * @date 2018/12/1 20:07
 */
public class FileUtils {

    public static String readFile(String filepath) {
        try {
            FileReader fis = new FileReader(filepath);  // 创建文件输入流
            BufferedReader br = new BufferedReader(fis);
            char[] data = new char[1024];               // 创建缓冲字符数组
            int rn;
            StringBuilder sb = new StringBuilder();       // 创建字符串构建器
            while ((rn = fis.read(data)) > 0) {         // 读取文件内容到字符串构建器
                String str = String.valueOf(data, 0, rn);//把数组转换成字符串
                sb.append(str);
            }
            fis.close();// 关闭输入流

            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 写入文件
     *
     * @param file
     * @param content
     */
    public static void writeFile(File file, String content) {
        createFile(file);

        FileOutputStream fileOutputStream = null;
        BufferedOutputStream buff = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            buff = new BufferedOutputStream(fileOutputStream);
            buff.write(content.getBytes());
            buff.flush();
            buff.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buff.close();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件，路径不存在先创建路径
     *
     * @param file
     * @return
     */
    public static boolean createFile(File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (!file.exists()) {
                return file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 递归获取所有子文件
     *
     * @param path
     * @param ignoreDirectory 忽略的文件夹
     * @return
     */
    public static List<File> traverseFolder(String path, String ignoreDirectory) {
        List<File> fileList = new ArrayList();

        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> directorys = new LinkedList<>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    if (!file2.getName().equals(ignoreDirectory)) {
                        directorys.add(file2);
                    }
                } else {
                    fileList.add(file2);
                }
            }
            File temp_file;
            while (!directorys.isEmpty()) {
                temp_file = directorys.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        directorys.add(file2);
                    } else {
                        fileList.add(file2);
                    }
                }
            }
        }
        return fileList;
    }
}
