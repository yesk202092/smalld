
package com.smalld.common.pojo;

import java.io.Serializable;

/**
 * 对Ajax请求返回Json格式数据的简易封装
 *
 * @author yesk
 * @date 2022/8/15 16:08
 **/
public class DataResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;


    private int code;

    private String msg;

    private T data;

    public DataResult() {
    }

    public DataResult(int code, String msg, T data) {
        this.setCode(code);
        this.setMsg(msg);
        this.setData(data);
    }

    /**
     * 获取code
     *
     * @return code
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * 获取msg
     *
     * @return msg
     */
    public String getMsg() {
        return this.msg;
    }

    /**
     * 获取data
     *
     * @return data
     */
    public T getData() {
        return this.data;
    }

    /**
     * 给code赋值，连缀风格
     *
     * @param code code
     * @return 对象自身
     */
    public DataResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * 给msg赋值，连缀风格
     *
     * @param msg msg
     * @return 对象自身
     */
    public DataResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 给data赋值，连缀风格
     *
     * @param data data
     * @return 对象自身
     */
    public DataResult<T> setData(T data) {
        this.data = data;
        return this;
    }


    // ============================  构建  ==================================

    // 构建成功
    public static <T> DataResult<T> success() {
        return new DataResult<>(CODE_SUCCESS, "操作成功", null);
    }

    public static <T> DataResult<T> success(String msg) {
        return new DataResult<>(CODE_SUCCESS, msg, null);
    }

    public static <T> DataResult<T> success(T data) {
        return new DataResult<>(CODE_SUCCESS, "操作成功", data);
    }

    // 构建失败
    public static <T> DataResult<T> error() {
        return new DataResult<>(CODE_ERROR, "请求失败", null);
    }

    public static <T> DataResult<T> error(String msg) {
        return new DataResult<>(CODE_ERROR, msg, null);
    }

    public static <T> DataResult<T> error(T data) {
        return new DataResult<>(CODE_ERROR, "请求失败", data);
    }

    public static <T> DataResult<T> error(T data, String msg) {
        return new DataResult<>(CODE_ERROR, msg, data);
    }

    // 构建指定状态码
    public static <T> DataResult<T> get(int code, String msg, T data) {
        return new DataResult<>(code, msg, data);
    }

    /*
     * toString()
     */
    @Override
    public String toString() {
        return "{"
                + "\"code\": " + this.getCode()
                + ", \"msg\": \"" + this.getMsg() + "\""
                + ", \"data\": \"" + this.getData() + "\""
                + "}";
    }
}
