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
    @Value("${wx.material-add}")
    private String addMaterialUtl;
    @Value("${wx.material-delete}")
    private String deleteMaterialUtl;

    public String accessToken;

    public String getAddMaterialUtl() {
        return addMaterialUtl.replace("%ACCESS_TOKEN%", accessToken);
    }

    public String getDeleteMaterialUtl() {
        return deleteMaterialUtl.replace("%ACCESS_TOKEN%", accessToken);
    }
}
