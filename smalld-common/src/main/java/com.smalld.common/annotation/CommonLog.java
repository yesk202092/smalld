
package com.smalld.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义日志注解
 *
 * @author yesk
 * @date 2022/6/20 14:25
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonLog {

    /**
     * 日志的名称，例如:"修改菜单"
     */
    String value() default "未命名";
}
