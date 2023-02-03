package com.niugiaogiao.utils;

import cn.hutool.http.HttpStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    private boolean success = true;

    /**
     * 返回处理消息
     */
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    private T result;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public R() {

    }

    public R<T> success(String message) {
        this.message = message;
        this.code = HttpStatus.HTTP_OK;
        this.success = true;
        return this;
    }


    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.setSuccess(true);
        r.setCode(HttpStatus.HTTP_OK);
        r.setMessage("成功");
        return r;
    }

    public static <T> R<T> ok(String msg) {
        R<T> r = new R<>();
        r.setSuccess(true);
        r.setCode(HttpStatus.HTTP_OK);
        r.setMessage(msg);
        return r;
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setSuccess(true);
        r.setCode(HttpStatus.HTTP_OK);
        r.setResult(data);
        return r;
    }

    public static <T> R<T> error(String msg) {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, msg);
    }

    public static <T> R<T> error(int code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public R<T> error500(String message) {
        this.message = message;
        this.code = HttpStatus.HTTP_INTERNAL_ERROR;
        this.success = false;
        return this;
    }

    /**
     * 无权限访问返回结果
     */
    public static <T> R<T> noAuth(String msg) {
        return error(HttpStatus.HTTP_BAD_REQUEST, msg);
    }
}