package com.smalld.archetype.common.util;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
//@ControllerAdvice(basePackages={"*controller*"})
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JsonResVo notFount(BizException e) {
        log.error("业务异常:", e);
        if (e.getErrorCode() == null) {
            e.setErrorCode("10000");
        }
        return JsonResVo.buildErrorResult(Integer.parseInt(e.getErrorCode()), e.getMessage());
    }

    /**
     * 参数类型转换错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JsonResVo invalidFormatException(InvalidFormatException e) {
        log.error("入参错误：", e);
        return JsonResVo.buildErrorResult(ErrorEnum.ERROR_PARAM_FORMAT.getErrorCode(),
                ErrorEnum.ERROR_PARAM_FORMAT.getErrorMessage());
    }

    /**
     * 拦截入参错误
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JsonResVo paramsException(MethodArgumentNotValidException e) {
        log.error("入参错误：", e);
        BindingResult bindingResult = e.getBindingResult();
        return JsonResVo.buildErrorResult(ErrorEnum.ERROR_PARAM.getErrorCode(),
                bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 拦截未知系统错误
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JsonResVo systemError(Exception e) {
        log.error("系统错误：", e);
        return JsonResVo.buildFail(ErrorEnum.SYSTEM_ERROR);
    }
}
