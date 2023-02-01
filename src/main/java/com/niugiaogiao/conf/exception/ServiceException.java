package com.niugiaogiao.conf.exception;

import cn.hutool.http.HttpStatus;

public final class ServiceException extends RuntimeException {

    private int serviceCode = 500;

    private ServiceException() {
    }

    private ServiceException(String msg) {
        super(msg);
    }

    private ServiceException(int serviceCode, String msg) {
        super(msg);
        this.serviceCode = serviceCode;
    }

    public static void parameterException() {
        exception(HttpStatus.HTTP_BAD_REQUEST, "参数异常");
    }

    public static void exception() {
        throw new ServiceException();
    }

    public static void exception(String msg) {
        throw new ServiceException(msg);
    }

    public static void exception(int serviceCode, String msg) {
        throw new ServiceException(serviceCode, msg);
    }

    public int getServiceCode() {
        return serviceCode;
    }
}
