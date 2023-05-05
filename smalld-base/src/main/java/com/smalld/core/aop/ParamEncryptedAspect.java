package com.smalld.core.aop;

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

    @Pointcut("execution(* com.smalld..Admin*Controller.*(..)) || execution(* com.smalld..admin..*Controller.*(..))")
    public void getMethod() {
    }
}
