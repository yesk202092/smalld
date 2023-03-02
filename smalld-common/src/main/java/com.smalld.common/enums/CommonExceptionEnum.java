
package com.smalld.common.enums;

import lombok.Getter;

/**
 * @author yesk
 * @date 2022/8/15 16:09
 **/
@Getter
public enum CommonExceptionEnum {

    OK200(200, "请求成功"),
    ERROR401(401, "未登录"),
    ERROR402(402, "token解析异常，请重新登录"),
    ERROR403(403, "无权限"),
    ERROR404(404, "路径不存在"),
    ERROR405(405, "请求方法不正确"),
    ERROR415(415, "参数传递异常"),
    ERROR500(500, "业务异常"),
    ;

    private final Integer code;

    private final String message;

    CommonExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
