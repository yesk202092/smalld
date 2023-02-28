package com.smalld.archetype.service;

import com.smalld.archetype.common.util.ZipUtils;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class BuildService {

    private static String artifactIdPathTarget = "${artifactId}";
    private static String projectNameTarget = "${projectName}";
    private static String groupPathTarget = "${groupPath}";
    private static String artifactPathTarget = "${artifactPath}";


    public void projectBuilds(String projectName,String artifactId, String groupPath, String artifactPath, OutputStream outputStream) {
        /** 1.创建临时文件夹  */
        File newTempDir = new File(this.getClass().getResource("/").getPath() + projectName);
        newTempDir.mkdirs();
        File oldTempDir = new File(this.getClass().getClassLoader().getResource("code.standard").getPath());
        //递归遍历模板文件，进行参数替换后并输出到临时文件夹中
        recursionReplace(projectName,newTempDir, oldTempDir, artifactId, groupPath, artifactPath);
        //压缩文件
        ZipUtils.toZip(newTempDir.getPath(), outputStream);

        //递归删除所有临时文件夹下文件
        recursionDelete(newTempDir);
        newTempDir.delete();
    }


    /**
     * 递归替换模板数据并输出到文件
     *
     * @param dir          要输出的文件夹
     * @param file         要读取的文件夹
     * @param artifactId   项目名称
     * @param groupPath    项目的group路径，通常都是com.公司域名
     * @param artifactPath 项目的包路径，暂时与artifactId相同
     */
    private void recursionReplace(String projectName,File dir, File file, String artifactId, String groupPath, String artifactPath) {
        File[] files = file.listFiles();
        if (file.isFile()) {
            return;
        }
        //用于存储当前文件夹路径，防止递归过程中目录结构出错
        File tempDir = dir;
        for (File f : files) {

            String newFileName = f.getName().replace(artifactIdPathTarget, artifactId).replace(groupPathTarget, groupPath).replace(artifactPathTarget, artifactPath).replace(projectNameTarget, projectName);
            StringBuffer buf = new StringBuffer();
            if (f.isFile()) {
                try (BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                ) {
                    // 替换所有匹配的字符串
                    for (String temp = null; (temp = bufReader.readLine()) != null; temp = null) {
                        if (temp.indexOf(artifactIdPathTarget) != -1) {
                            temp = temp.replace(artifactIdPathTarget, artifactId);
                        }
                        if (temp.indexOf(projectNameTarget) != -1) {
                            temp = temp.replace(projectNameTarget, projectName);
                        }
                        if (temp.indexOf(groupPathTarget) != -1) {
                            temp = temp.replace(groupPathTarget, groupPath);
                        }
                        if (temp.indexOf(artifactPathTarget) != -1) {
                            temp = temp.replace(artifactPathTarget, artifactPath);
                        }
                        buf.append(temp);
                        //行与行之间的分割
                        buf.append(System.getProperty("line.separator"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                //如果是文件夹，则把"."替换成文件夹路径
                if (f.isDirectory()) {
                    newFileName = newFileName.replace(".", File.separator);
                }
                //替换以file开头的文件名
                if (newFileName.startsWith("file")) {
                    newFileName = newFileName.replace("file", "");
                }
                File outFile = new File(dir.getAbsolutePath(), newFileName);
                //如果是文件夹，则开始创建文件夹，并把路径指向该文件夹
                if (f.isDirectory()) {
                    outFile.mkdirs();
                    tempDir = outFile;
                } else {
                    //如果是文件则输出文件内容到该文件
                    try (FileWriter pw = new FileWriter(outFile)) {
                        pw.write(buf.toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //递归遍历文件
            recursionReplace(projectName,tempDir, f, artifactId, groupPath, artifactPath);
        }
    }

    /**
     * 递归删除所有临时文件
     *
     * @param dir 临时文件夹
     */
    private void recursionDelete(File dir) {
        if (dir.isFile()) {
            dir.delete();
            return;
        }
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            dir.delete();
            return;
        }
        for (File file : files) {
            recursionDelete(file);
        }
        dir.delete();
    }


}
