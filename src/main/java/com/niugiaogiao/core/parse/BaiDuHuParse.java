package com.niugiaogiao.core.parse;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.niugiaogiao.core.enums.Platform;
import com.niugiaogiao.core.hotspot.HotSpotResponse;
import com.niugiaogiao.modules.hotspot.vo.HotSpotBaiDuVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaiDuHuParse implements HotSpotParse {
    @Override
    public boolean isParse(HotSpotResponse hotSpotResponse) {
        return Platform.BAI_DU.equals(hotSpotResponse.getPlantFlag());
    }

    @Override
    public List<BaseParseResult> parse(JSONObject hotSpotResponse) {
        JSONArray jsonArray = (JSONArray) hotSpotResponse.get("data");
        List<HotSpotBaiDuVO> bandLists = jsonArray.toList(HotSpotBaiDuVO.class);
        List<BaseParseResult> baseList = new ArrayList<>();
        bandLists.forEach(item -> {
            BaseParseResult newsInfo = new BaseParseResult();
            newsInfo.setTitle(item.getWord());
            newsInfo.setAccessUrl(item.getUrl());
            newsInfo.setTopicId(IdUtil.simpleUUID());
            baseList.add(newsInfo);
        });
        return baseList;
    }
}
