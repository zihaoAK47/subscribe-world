package com.niugiaogiao.core.hotspot.source;

import com.niugiaogiao.core.enums.Platform;
import com.niugiaogiao.core.hotspot.AbstractHotSpot;
import com.niugiaogiao.core.hotspot.HotSpotAPiSource;
import org.springframework.stereotype.Service;

/**
 * 微博热点数据
 */
@Service
class WeiBoHotSpot extends AbstractHotSpot implements HotSpotAPiSource {

    private static final String wbHotSpot = "https://weibo.com/ajax/statuses/hot_band";

    public WeiBoHotSpot() {
        super(wbHotSpot, Platform.WEI_BO);
    }
}
