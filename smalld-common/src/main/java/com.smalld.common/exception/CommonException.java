
package com.smalld.common.exception;

import cn.hutool.core.util.StrUtil;
import com.smalld.common.enums.CommonExceptionEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 通用异常
 *
 * @author yesk
 * @date 2020/4/8 15:54
 */
@Getter
@Setter
public class CommonException extends RuntimeException {

    private Integer code;

    private String msg;

    public CommonException() {
        super("服务器异常");
        this.code = 500;
        this.msg = "服务器异常";
    }

    public CommonException(String msg, Object... arguments) {
        super(StrUtil.format(msg, arguments));
        this.code = 500;
        this.msg = StrUtil.format(msg, arguments);
    }

    public CommonException(Integer code, String msg, Object... arguments) {
        super(StrUtil.format(msg, arguments));
        this.code = code;
        this.msg = StrUtil.format(msg, arguments);
    }

    public CommonException(CommonExceptionEnum commonExceptionEnum) {
        super(commonExceptionEnum.getMessage());
        this.code = commonExceptionEnum.getCode();
        this.msg = commonExceptionEnum.getMessage();
    }
}
