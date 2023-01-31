package com.niugiaogiao.core.parse;

import com.alibaba.fastjson2.JSONObject;
import com.niugiaogiao.core.hotspot.HotSpotResponse;

import java.util.List;

public interface HotSpotParse {

    boolean isParse(HotSpotResponse hotSpotResponse);

    List<BaseParseResult> parse(JSONObject hotSpotResponse);
}
