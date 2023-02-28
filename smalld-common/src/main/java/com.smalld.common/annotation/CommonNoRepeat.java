
package com.smalld.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义节流防抖注解
 *
 * @author yesk
 * @date 2022/6/20 14:25
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonNoRepeat {

    /**
     * 间隔时间(ms)，小于此时间视为重复提交，默认5000ms
     */
    int interval() default 5000;
}
