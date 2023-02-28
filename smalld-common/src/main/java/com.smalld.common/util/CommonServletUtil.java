
package com.smalld.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.smalld.common.exception.CommonException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpServlet工具类，获取当前request和response
 *
 * @author yesk
 * @date 2020/3/30 15:09
 */
public class CommonServletUtil {

    /**
     * 从请求中中获取参数
     *
     * @author yesk
     * @date 2021/10/14 10:44
     **/
    public static String getParamFromRequest(String paramName) {
        HttpServletRequest request = getRequest();

        // 1. 尝试从请求体里面读取
        String paramValue = request.getParameter(paramName);

        // 2. 尝试从header里读取
        if (ObjectUtil.isEmpty(paramValue)) {
            paramValue = request.getHeader(paramName);
        }
        // 3. 尝试从cookie里读取
        if (ObjectUtil.isEmpty(paramValue)) {
            Cookie[] cookies = request.getCookies();
            if(ObjectUtil.isNotEmpty(cookies)) {
                for (Cookie cookie : cookies) {
                    String cookieName = cookie.getName();
                    if (cookieName.equals(paramName)) {
                        return cookie.getValue();
                    }
                }
            }
        }
        // 4. 返回
        return paramValue;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes;
        try {
            servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException("非Web上下文无法获取Request");
        }
        if (servletRequestAttributes == null) {
            throw new CommonException("非Web上下文无法获取Request");
        } else {
            return servletRequestAttributes.getRequest();
        }
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes;
        try {
            servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException("非Web上下文无法获取Response");
        }
        if (servletRequestAttributes == null) {
            throw new CommonException("非Web上下文无法获取Response");
        } else {
            return servletRequestAttributes.getResponse();
        }
    }

    public static boolean isWeb() {
        return RequestContextHolder.getRequestAttributes() != null;
    }
}
