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
    private String addMaterialUrl;
    @Value("${wx.material-delete}")
    private String deleteMaterialUrl;

    @Value("${wx.material-list}")
    private String listMaterialUrl;

    public String accessToken;

    public String getAddMaterialUrl() {
        return addMaterialUrl.replace("%ACCESS_TOKEN%", accessToken);
    }

    public String getDeleteMaterialUrl() {
        return deleteMaterialUrl.replace("%ACCESS_TOKEN%", accessToken);
    }

    public String getListMaterialUrl() {
        return listMaterialUrl.replace("%ACCESS_TOKEN%", accessToken);
    }
}
