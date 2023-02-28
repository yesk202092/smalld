package com.smalld.archetype.manager;


import com.smalld.archetype.common.util.BizException;
import com.smalld.archetype.common.util.FileUtils;
import com.smalld.archetype.common.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yichuang.chen
 */

@Service
@Slf4j
public class CodeManager {

    /**
     * 源码模板路径，如有变化，请在服务器该目录上，git拉最新的demo代码，
     * git地址：https://gitlab.shuimuzhineng.com/backend/java-framework/smalld-demo.git
     */
    @Value("${smalld.code.input:xxx}")
    private String inputPath;

    /**
     * 输出代码路径
     */
    @Value("${smalld.code.output:xxx}")
    private String outputPath;

    /**
     * 生成脚手架代码
     *
     * @param projectName
     * @return 生成压缩包的绝对路径
     */
    public String generateCode(String projectName) {
        if ("xxx".equals(inputPath)) {
            throw new BizException("当前环境不支持");
        }
        Pattern pattern = Pattern.compile("^[a-z][a-z\\-]*[a-z]+$");
        Matcher matcher = pattern.matcher(projectName);
        if (!matcher.find() || projectName.contains("--")) {
            throw new BizException("项目名只能包含a-z、-，开始和结束必须是字母，不能包含连续的-");
        }

        String newPath = outputPath + "/" + projectName;
        String outputZipFile = newPath + ".zip";
        if (new File(outputZipFile).exists()) {
            throw new BizException("已存在" + projectName + "项目，请换个名字");
        }

        String dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //新存放路径

        File inputDir = new File(inputPath);
        if (!inputDir.exists() || !inputDir.isDirectory()) {
            throw new BizException(inputPath + "目录不存在，请先登录服务器上传源码模板");
        }
        List<File> fileList = FileUtils.traverseFolder(inputPath, ".git");
        for (File file : fileList) {
            String content = FileUtils.readFile(file.getAbsolutePath());
            content = content.replaceAll("smalld-demo", projectName);
            content = content.replaceAll("SmalldDemo", captureFirstName(projectName));
            content = content.replaceAll("smalldDemo", camelCase(projectName));
            content = content.replaceAll("SMALLD_DEMO", projectName.replace("-", "_").toUpperCase());
            content = content.replaceAll("smalld\\.demo", projectName.replace("-", ".").replaceAll("_", ".").toLowerCase());
            content = content.replaceAll("@date \\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}", "@date " + dateFormat);

            String newFilePath = newPath + file.getAbsolutePath().substring(inputPath.length());
            newFilePath = newFilePath.replaceAll("smalld/demo", projectName.replace("-", "/").replaceAll("_", "/"));
            newFilePath = newFilePath.replaceAll("SmalldDemo", captureFirstName(projectName));
            File newFile = new File(newFilePath);
            FileUtils.writeFile(newFile, content);
            //清空内存文件
            file = null;
            content = null;
        }
        //清空内存文件
        fileList = null;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(outputZipFile));
            ZipUtil.compressDir(newPath, fos, true);
        } catch (FileNotFoundException e) {
            log.error("文件未找到：{}", e.getMessage());
            throw new BizException(e.getMessage());
        } catch (Exception e) {
            log.error("压缩输出异常：{}", e.getMessage());
            throw new BizException(e.getMessage());
        }
        return outputZipFile;

    }

    /**
     * 首字母大写
     *
     * @param name
     * @return
     */
    public static String captureFirstName(String name) {
        String[] split = name.split("-");
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : split) {
            stringBuilder.append(str.substring(0, 1).toUpperCase() + str.substring(1));
        }
        return stringBuilder.toString();
    }

    /**
     * 驼峰
     *
     * @param name
     * @return
     */
    public static String camelCase(String name) {
        String[] split = name.split("-");
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String str : split) {
            if (i == 0) {
                i++;
                stringBuilder.append(str);
                continue;
            }
            stringBuilder.append(str.substring(0, 1).toUpperCase() + str.substring(1));
        }
        return stringBuilder.toString();
    }
}
