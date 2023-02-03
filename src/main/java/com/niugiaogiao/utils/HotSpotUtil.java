package com.niugiaogiao.utils;

import com.niugiaogiao.core.parse.BaseParseResult;
import com.niugiaogiao.core.save.HotSpotCacheKey;
import com.niugiaogiao.modules.hotspot.vo.HotSpotWeiVO;
import com.niugiaogiao.modules.hotspot.vo.HotSpotZhiHuVO;
import lombok.AllArgsConstructor;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public final class HotSpotUtil {

    private final RedisUtil redisUtil;

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

    public String wxHotSpotZhiHu() {
        StringBuilder sb = new StringBuilder();
        List<HotSpotZhiHuVO> hotSpotZhiHuVOS = hotSpotZhiHu();
        for (HotSpotZhiHuVO item : hotSpotZhiHuVOS) {
            sb.append(item.getTitle()).append("\n\n");
        }
        return sb.toString();
    }

    public String wxHotSpotWeiBo() {
        StringBuilder sb = new StringBuilder();
        List<HotSpotWeiVO> hotSpotWeiVOS = hotSpotWeiBo();
        for (HotSpotWeiVO item : hotSpotWeiVOS) {
            sb.append(item.getTitle()).append("\n\n");
        }
        return sb.toString();
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
