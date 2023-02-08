package com.niugiaogiao.core.hotspot.source;

import com.niugiaogiao.core.enums.Platform;
import com.niugiaogiao.core.hotspot.AbstractHotSpot;
import com.niugiaogiao.core.hotspot.HotSpotAPiSource;
import org.springframework.stereotype.Service;

@Service
public class BaiDuHotSpot extends AbstractHotSpot implements HotSpotAPiSource {

    private static final String wbHotSpot = "https://api.1314.cool/getbaiduhot/";

    public BaiDuHotSpot() {
        super(wbHotSpot, Platform.BAI_DU);
    }
}
