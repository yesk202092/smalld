
package com.smalld.common.annotation;

import com.smalld.common.pojo.CommonWrapperInterface;

import java.lang.annotation.*;

/**
 * 自定义包装注解，对响应结果包装
 *
 * @author yesk
 * @date 2022/9/15 21:12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CommonWrapper {

    /**
     * 具体包装类
     */
    Class<? extends CommonWrapperInterface<?>>[] value();
}
