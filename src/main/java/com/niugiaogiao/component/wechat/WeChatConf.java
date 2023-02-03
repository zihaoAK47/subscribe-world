package com.niugiaogiao.component.wechat;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class WeChatConf {

    @Value("${wx.app-id}")
    public String appId;
    @Value("${wx.app-secret}")
    public String appSecret;
    @Value("${wx.access-token}")
    public String tokenUrl;
    public String accessToken;
}
