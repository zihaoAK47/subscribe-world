package com.niugiaogiao.conf.exception;

import com.niugiaogiao.utils.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
class GlobalExceptionConf {

    private GlobalExceptionConf() {
    }

    @ExceptionHandler(Exception.class)
    public Result<?> unknownSystemException(Exception e) {
        return Result.error("未知系统异常");
    }

    @ExceptionHandler(ServiceException.class)
    public Result<?> serviceException(ServiceException serviceException) {
        String message = serviceException.getMessage();
        return StringUtils.isEmpty(message)
                ? Result.error("业务异常")
                : Result.error(serviceException.getServiceCode(), message);
    }

    @ExceptionHandler(SystemException.class)
    public Result<?> systemException(SystemException systemException) {
        String message = systemException.getMessage();
        return StringUtils.isEmpty(message)
                ? Result.error("系统异常")
                : Result.error(systemException.getServiceCode(), message);
    }
}
