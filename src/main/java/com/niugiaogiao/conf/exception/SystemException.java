package com.niugiaogiao.conf.exception;

public final class SystemException extends RuntimeException {

    private int serviceCode = 500;

    private SystemException(){}

    private SystemException(String msg) {
        super(msg);
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public static void exception() {
        throw new SystemException();
    }

    public static void exception(String msg) {
        throw new SystemException(msg);
    }

}
