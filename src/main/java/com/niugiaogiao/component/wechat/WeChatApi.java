package com.niugiaogiao.component.wechat;

public interface WeChatApi {

    String addForeverMaterial(String fileName);
    void getForeverMaterial();
    void getForeverMaterialList();
    void deleteForeverMaterial(String mediaId);
    String listForeverMaterial();
}
