package com.niugiaogiao.component.wechat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatConf {

    @Value("${wx.app-id}")
    public String appId;
    @Value("${wx.app-secret}")
    public String appSecret;
    @Value("${wx.access-token}")
    public String tokenUrl;
    @Value("${wx.material-upload}")
    private String uploadMaterial;

    public String accessToken;

    public String getUploadForeverMaterialUrl() {
        return uploadMaterial.replace("%ACCESS_TOKEN%", accessToken);
    }
}
