package com.niugiaogiao.component.wechat;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.niugiaogiao.utils.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@AllArgsConstructor
final class WeChatAccessTokenUtil {

    private final RedisUtil redisUtil;
    private final WeChatConf weChatConf;
    private static final String WX_TOKEN_KEY = "wx:access_token";
    private static final int expressTime = 7200;

    public void accessToken() {
        String accessToken = (String) redisUtil.get(WX_TOKEN_KEY);
        if (!StringUtils.isEmpty(accessToken)) {
            weChatConf.accessToken = accessToken;
            return;
        }

        weChatConf.accessToken = getToken();
        redisUtil.set(WX_TOKEN_KEY, weChatConf.accessToken, expressTime);
    }

    private String getToken() {
        String response = HttpUtil.get(weChatConf.tokenUrl);
        JSONObject jsonObject = JSONObject.parseObject(response);
        return (String) jsonObject.get("access_token");
    }
}
