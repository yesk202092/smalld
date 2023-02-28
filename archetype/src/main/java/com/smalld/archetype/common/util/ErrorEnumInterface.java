package com.smalld.archetype.common.util;

/**
 * 各个模块自定义的异常码，继承本接口后，可以调用ExceptionUtil.buildException(ErrorEnumInterface errorEnum)快速创建一个异常
 * @author yichuang.chen
 * @date 2018/12/13 3:37 PM
 */
public interface ErrorEnumInterface {

    String getErrorMessage();

    int getErrorCode();

    int getParentCode();
}
