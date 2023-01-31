package com.niugiaogiao.core.hotspot;

import cn.hutool.http.HttpUtil;
import com.niugiaogiao.core.enums.Platform;

public class AbstractHotSpot implements HotSpot {

    private final String url;
    private final Platform platform;

    public AbstractHotSpot(final String url, Platform platform) {
        this.url = url;
        this.platform = platform;
    }

    @Override
    public Platform getPlantFlag() {
        return platform;
    }

    @Override
    public String downDataSource() {
        return HttpUtil.get(url);
    }
}
