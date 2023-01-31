package com.niugiaogiao.core.hotspot;

import com.alibaba.fastjson2.JSONObject;
import com.niugiaogiao.core.enums.Platform;
import lombok.Data;

@Data
public class HotSpotResponse {
    private final Platform plantFlag;
    private final JSONObject hostSpotVal;
}
