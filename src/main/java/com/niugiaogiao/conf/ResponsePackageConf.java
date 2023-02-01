package com.niugiaogiao.conf;

import cn.hutool.json.JSONUtil;
import com.niugiaogiao.utils.IgnoreResponseResult;
import com.niugiaogiao.utils.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"com.niugiaogiao.modules"})
class ResponsePackageConf implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof IgnoreResponseResult)
            return ((IgnoreResponseResult<?>) o).getResult();

        return o instanceof String ? JSONUtil.toJsonStr(Result.ok(o)) : Result.ok(o);
    }
}
