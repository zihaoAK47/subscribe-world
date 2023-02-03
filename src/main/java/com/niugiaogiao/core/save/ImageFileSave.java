package com.niugiaogiao.core.save;

import com.niugiaogiao.component.wechat.WeChatApi;
import com.niugiaogiao.core.enums.Platform;
import com.niugiaogiao.core.parse.BaseParseResult;
import com.niugiaogiao.utils.ImagesUtil;
import com.niugiaogiao.utils.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
final class ImageFileSave implements HotSpotSave {

    private final RedisUtil redisUtil;
    private final WeChatApi weChatApi;
    private final ImagesUtil imagesUtil;

    @Override
    public <T> void save(Platform platform, T parseResult) {
        String fileName = "/home/niugiaogiao/".concat(platform.name()).concat(".JPEG");
        if (createHotSpotMaterial(parseResult, fileName)) {
            uploadWxMaterial(platform, fileName);
        }
    }

    private <T> boolean createHotSpotMaterial(T parseResult, String fileName) {
        if (!(parseResult instanceof List)) {
            return false;
        }
        List<BaseParseResult> hotSpotResult = CastUtils.cast(parseResult);
        List<String> titles = new ArrayList<>(hotSpotResult.size());
        hotSpotResult.forEach(item -> titles.add(item.getTitle()));
        imagesUtil.textToImage(titles, new File(fileName));
        return true;
    }

    private void uploadWxMaterial(Platform platform, String fileName) {
        String redisKey = "wx:material:".concat(platform.name());
        String mediaId = (String) redisUtil.get(redisKey);
        if (!StringUtils.isEmpty(mediaId)) {
            weChatApi.deleteForeverMaterial(mediaId);
        }

        mediaId = weChatApi.addForeverMaterial(fileName);
        if (StringUtils.isEmpty(mediaId))
            return;
        redisUtil.set(redisKey, mediaId);
    }
}
