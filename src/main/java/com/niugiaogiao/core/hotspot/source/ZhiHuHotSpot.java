package com.niugiaogiao.core.hotspot.source;

import com.niugiaogiao.core.enums.Platform;
import com.niugiaogiao.core.hotspot.AbstractHotSpot;
import com.niugiaogiao.core.hotspot.HotSpotAPiSource;
import org.springframework.stereotype.Service;

/**
 * 知乎热点数据
 */
@Service
class ZhiHuHotSpot extends AbstractHotSpot implements HotSpotAPiSource {

    private static final String hotSpotUrl = "https://www.zhihu.com/api/v4/creators/rank/hot?domain=0&period=hour";

    public ZhiHuHotSpot() {
        super(hotSpotUrl, Platform.ZHI_HU);
    }
}
