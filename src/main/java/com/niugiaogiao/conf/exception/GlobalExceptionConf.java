package com.niugiaogiao.conf.exception;

import com.niugiaogiao.utils.R;
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
    public R<?> unknownSystemException(Exception e) {
        return R.error("未知系统异常");
    }

    @ExceptionHandler(ServiceException.class)
    public R<?> serviceException(ServiceException serviceException) {
        String message = serviceException.getMessage();
        return StringUtils.isEmpty(message)
                ? R.error("业务异常")
                : R.error(serviceException.getServiceCode(), message);
    }

    @ExceptionHandler(SystemException.class)
    public R<?> systemException(SystemException systemException) {
        String message = systemException.getMessage();
        return StringUtils.isEmpty(message)
                ? R.error("系统异常")
                : R.error(systemException.getServiceCode(), message);
    }
}
