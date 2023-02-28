package com.smalld.archetype.common.util;

/**
 * 错误码
 * @author jim
 *
 */
public enum ErrorEnum {
    /**
     * 错误码分内外两种
     * 对内使用最细粒度错误吗，对外使用统一错误码
     * 对外统一使用本类型第一个错误码。
     */
    //1开头返回接收细节错误码,其它返回父错误码
    SYSTEM_ERROR(10000, 10000, "系统错误"),
    REQUEST_INVALID(11000, 11000, "无效请求"),
    ERROR_BIZ_FAIL(20000, 20001, "业务失败"),
    ERROR_WRITE(500, 501, "渲染界面错误"),
    ERROR_PARAM(12000, 12002, "参数校验错误"),
    ERROR_PARAM_FORMAT(12000, 12003, "参数类型错误"),
    ERROR_TOKEN_VALID(14000, 14001, "Token过期"),
    ERROR_TOKEN_CHECK(14000, 14002, "Token验证异常")
    ;

    private final int errorCode;
    private final int parentCode;
    private final String errorMessage;

    ErrorEnum(int parentCode, int errorCode, String errorMessage) {
        this.parentCode = parentCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getParentCode() {
        if (String.valueOf(errorCode).startsWith("1")) {
            return errorCode;
        }

        return parentCode;
    }

    public ErrorEnum getOutError() {
        return getErrorByCode(getParentCode());
    }

    public static ErrorEnum getErrorByCode(int code) {
        for (ErrorEnum errorEnum : values()) {
            if (errorEnum.getErrorCode() == code) {
                return errorEnum;
            }
        }
        return null;
    }
}

