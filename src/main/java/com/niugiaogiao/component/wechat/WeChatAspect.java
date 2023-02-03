package com.niugiaogiao.component.wechat;

import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
final class WeChatAspect {

    private final WeChatAccessTokenUtil weChatAccessTokenUtil;


    @Pointcut("execution(* com.niugiaogiao.component.wechat.WeChatApi.*(..))")
    private void commonPoint() {
    }

    @Before("commonPoint()")
    private void checkToken(JoinPoint joinPoint) {
        weChatAccessTokenUtil.accessToken();
    }
}
