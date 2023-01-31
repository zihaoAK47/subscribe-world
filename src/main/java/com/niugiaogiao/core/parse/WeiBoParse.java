package com.niugiaogiao.core.parse;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.niugiaogiao.core.enums.Platform;
import com.niugiaogiao.core.hotspot.HotSpotResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class WeiBoParse implements HotSpotParse {

    @Override
    public boolean isParse(HotSpotResponse hotSpotResponse) {
        return Platform.WEI_BO.equals(hotSpotResponse.getPlantFlag());
    }

    @Override
    public List<BaseParseResult> parse(JSONObject hostSpotVal) {
        JSONObject jsonData = (JSONObject) hostSpotVal.get("data");
        JSONArray bindLists = jsonData.getJSONArray("band_list");
        List<BandList> bandLists = bindLists.toJavaList(BandList.class);

        List<BaseParseResult> weiBoParseResults = new ArrayList<>(bandLists.size());
        bandLists.forEach( item -> {
            WeiBoParseResult newsInfo = new WeiBoParseResult();
            newsInfo.setTitle(item.getWord());
            newsInfo.setAccessUrl("https://s.weibo.com/weibo?q=".concat(item.getWord()));
            newsInfo.setTopicId(IdUtil.simpleUUID());
            weiBoParseResults.add(newsInfo);
        });

        return weiBoParseResults;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    static class WeiBoParseResult extends BaseParseResult {
    }

    @Data
    static class BandList {
        private String word;
    }
}
