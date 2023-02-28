package com.smalld.archetype.controller;

import com.smalld.archetype.common.util.ErrorEnum;
import com.smalld.archetype.service.BuildService;
import com.smalld.archetype.manager.CodeManager;
import com.smalld.archetype.common.util.DownloadUtil;
import com.smalld.archetype.common.util.JsonResVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
@RequestMapping
public class ArchetypeController {

    private static Logger log = LoggerFactory.getLogger(ArchetypeController.class);

    public volatile static int nums = 0;

    public static final Object LOCK = new Object();


    @Resource
    private BuildService buildService;

    @Autowired
    private CodeManager codeManager;


    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/productBuild")
    @ResponseBody
    public void productBuild(@RequestParam("projectName") String projectName,@RequestParam(value = "artifactId")String artifactId, String groupPath, String artifactPath, HttpServletResponse response) {
        if (groupPath == null || groupPath.length() == 0) {
            groupPath = "com.smalld";
        }
        if (artifactPath == null || artifactPath.length() == 0) {
            artifactPath = artifactId;
        }
        try {
            buildService.projectBuilds(projectName,artifactId, groupPath, artifactPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("项目生成异常，msg:{}", e.getMessage(), e);
        }
    }

    /**
     * 新版脚手架代码生成
     *
     * @param projectName
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/projectBuild/v2", method = RequestMethod.GET)
    public JsonResVo generateTsfCode(@RequestParam(value = "name") String projectName,
                                     HttpServletResponse response,
                                     HttpServletRequest request) {


        if (nums > 0) {
            return JsonResVo.buildErrorResult(ErrorEnum.REQUEST_INVALID.getErrorCode(), "正在生成项目，请勿重复请求");
        }
        synchronized (LOCK) {
            nums++;
            try {
                String filePath = codeManager.generateCode(projectName);
                DownloadUtil.downLoadFile(request, response, filePath.substring(filePath.lastIndexOf("/") + 1), new File(filePath));
            } finally {
                nums--;
            }
        }
        return null;
    }


}
