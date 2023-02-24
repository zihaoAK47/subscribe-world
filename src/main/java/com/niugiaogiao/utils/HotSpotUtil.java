package com.niugiaogiao.utils;

import com.niugiaogiao.core.parse.BaseParseResult;
import com.niugiaogiao.core.save.HotSpotCacheKey;
import com.niugiaogiao.modules.hotspot.vo.HotSpotWeiVO;
import com.niugiaogiao.modules.hotspot.vo.HotSpotZhiHuVO;
import lombok.AllArgsConstructor;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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

    public void saveLastOperator(final String fromUser, final String operator) {
        redisUtil.set(HotSpotCacheKey.HOT_SPOT_USER_OPERATOR.concat(fromUser), operator, 20 * 60);
    }

    public boolean existsUserLastOperator(final String fromUser) {
        return redisUtil.hasKey(HotSpotCacheKey.HOT_SPOT_USER_OPERATOR.concat(fromUser));
    }

    public BaseParseResult getHotSpotDetail(final String fromUser, final String content) {
        Object lastOperator = redisUtil.get(HotSpotCacheKey.HOT_SPOT_USER_OPERATOR.concat(fromUser));
        if (ObjectUtils.isEmpty(lastOperator)) {
            return null;
        }

        int index = 0;
        List<BaseParseResult> baseHotSpotZhiHu = null;
        if ("a".equals(lastOperator)) {
            baseHotSpotZhiHu = getBaseParseResult(HotSpotCacheKey.HOT_SPOT_ZHI_HU);
            for (BaseParseResult item : baseHotSpotZhiHu) {
                if (String.valueOf((++index)).equals(content)) {
                    return item;
                }
            }
        }

        if ("b".equals(lastOperator)) {
            baseHotSpotZhiHu = getBaseParseResult(HotSpotCacheKey.HOT_SPOT_WEI_BO);
            for (BaseParseResult item : baseHotSpotZhiHu) {
                if (String.valueOf((++index)).equals(content)) {
                    return item;
                }
            }
        }

        return null;
    }

    private List<BaseParseResult> getBaseParseResult(final String key) {
        Object cacheObject = redisUtil.get(key);
        if (ObjectUtils.isEmpty(cacheObject))
            return Collections.emptyList();

        return CastUtils.cast(cacheObject);
    }
}
