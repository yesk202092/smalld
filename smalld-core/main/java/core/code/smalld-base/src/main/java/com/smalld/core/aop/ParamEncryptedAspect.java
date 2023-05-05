package com.smalld.core.code.smalld;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 判断是否需要加密
 * 如果加密则将入参 解密并丢进入参里
 * @author yesk
 */
@Component
@Order(3)
@Aspect
public class ParamEncryptedAspect {

    @Pointcut("@annotation(com.smalld.electric.api.annotation.ApiClickLock)")
    public void getMethod() {
    }
}
