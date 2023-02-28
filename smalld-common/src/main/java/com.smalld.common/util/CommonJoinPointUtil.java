
package com.smalld.common.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring切面工具类
 *
 * @author yesk
 * @date 2022/9/2 15:51
 */
public class CommonJoinPointUtil {

    /**
     * 获取切面的参数JSON
     *
     * @author yesk
     * @date 2022/9/2 15:51
     */
    public static String getArgsJsonString(JoinPoint joinPoint) {
        StringBuilder argsJson = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (!isFilterObject(arg)) {
                if (ObjectUtil.isNotNull(arg)) {
                    argsJson.append(JSONUtil.toJsonStr(arg)).append(" ");
                }
            }
        }
        return argsJson.toString().trim();
    }

    /**
     * 判断是否需要拼接参数，过滤掉HttpServletRequest,MultipartFile,HttpServletResponse等类型参数
     *
     * @author yesk
     * @date 2022/9/2 15:51
     */
    private static boolean isFilterObject(Object arg) {
        return arg instanceof MultipartFile ||
                arg instanceof HttpServletRequest ||
                arg instanceof HttpServletResponse;
    }
}
