package com.niugiaogiao.core.save;

import com.niugiaogiao.core.enums.Platform;
import com.niugiaogiao.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RedisSave implements HotSpotSave {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public <T> void save(Platform platform, T parResult) {
        String redisKey = HotSpotCacheKey.HOT_SPOT_FLAG.concat(platform.name());
        redisUtil.set(redisKey, parResult);
    }
}
