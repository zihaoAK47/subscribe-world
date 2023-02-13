package com.niugiaogiao.component.wechat;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.niugiaogiao.conf.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;

@Component
@AllArgsConstructor
class WeChatApiDefault implements WeChatApi {

    private final WeChatConf weChatConf;

    @Override
    public String addForeverMaterial(String fileName) {
        if (StringUtils.isEmpty(fileName))
            return null;

        String uploadForeverMaterialUrl = weChatConf.getAddMaterialUrl();
        try (HttpResponse httpResponse = HttpRequest
                .post(uploadForeverMaterialUrl)
                .form("media", new File(fileName))
                .execute()) {

            if (httpResponse.getStatus() == HttpStatus.HTTP_OK) {
                return StringUtils.isEmpty(httpResponse.body())
                        ? null
                        : (String) JSONObject.parseObject(httpResponse.body()).get("media_id");
            }
        } catch (Exception e) {
            ServiceException.exception("forever material upload error.");
        }

        return null;
    }

    @Override
    public void getForeverMaterial() {
    }

    @Override
    public void getForeverMaterialList() {
    }

    @Override
    public void deleteForeverMaterial(String mediaId) {
        if (StringUtils.isEmpty(mediaId))
            return;

        JSONObject body = new JSONObject();
        body.put("media_id", mediaId);
        HttpUtil.post(weChatConf.getDeleteMaterialUrl(), body.toJSONString());
    }

    @Override
    public String listForeverMaterial() {
        JSONObject reqBody = new JSONObject();
        reqBody.put("type", "image");
        reqBody.put("offset", 0);
        reqBody.put("count", 10);
        return HttpUtil.post(weChatConf.getListMaterialUrl(), reqBody.toString());
    }
}
