package com.niugiaogiao.utils;

import com.niugiaogiao.core.parse.BaseParseResult;
import com.niugiaogiao.core.save.HotSpotCacheKey;
import com.niugiaogiao.modules.hotspot.vo.HotSpotWeiVO;
import com.niugiaogiao.modules.hotspot.vo.HotSpotZhiHuVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public final class HotSpotUtil {

    @Autowired
    final RedisUtil redisUtil;

    public List<HotSpotZhiHuVO> hotSpotZhiHu() {
        List<BaseParseResult> baseParseResult = getBaseParseResult(HotSpotCacheKey.HOT_SPOT_ZHI_HU);
        List<HotSpotZhiHuVO> result = new ArrayList<>(baseParseResult.size());
        baseParseResult.forEach(item -> {
            HotSpotZhiHuVO itemResult = new HotSpotZhiHuVO();
            itemResult.setTitle(item.getTitle());
            itemResult.setTopicId(item.getTopicId());
            result.add(itemResult);
        });

        return result;
    }

    public List<HotSpotWeiVO> hotSpotWeiBo() {
        List<BaseParseResult> baseParseResult = getBaseParseResult(HotSpotCacheKey.HOT_SPOT_WEI_BO);
        List<HotSpotWeiVO> result = new ArrayList<>(baseParseResult.size());
        baseParseResult.forEach(item -> {
            HotSpotWeiVO itemResult = new HotSpotWeiVO();
            itemResult.setTitle(item.getTitle());
            itemResult.setTopicId(item.getTopicId());
            result.add(itemResult);
        });

        return result;
    }

    private List<BaseParseResult> getBaseParseResult(final String key) {
        Object cacheObject = redisUtil.get(key);
        if (ObjectUtils.isEmpty(cacheObject))
            return Collections.emptyList();

        return CastUtils.cast(cacheObject);
    }
}
