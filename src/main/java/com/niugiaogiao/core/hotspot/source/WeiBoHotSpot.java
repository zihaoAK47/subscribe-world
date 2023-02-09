package com.niugiaogiao.core.hotspot.source;

import com.niugiaogiao.core.enums.Platform;
import com.niugiaogiao.core.hotspot.AbstractHotSpot;
import com.niugiaogiao.core.hotspot.HotSpotAPiSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 微博热点数据
 */
@Service
class WeiBoHotSpot extends AbstractHotSpot implements HotSpotAPiSource {

    @Autowired
    private RestTemplate restTemplate;

    private static final String wbHotSpot = "https://weibo.com/ajax/statuses/hot_band";

    public WeiBoHotSpot() {
        super(wbHotSpot, Platform.WEI_BO);
    }

    @Override
    public String downDataSource() {
        ResponseEntity<String> response = restTemplate.getForEntity(wbHotSpot, String.class);
        return response.getBody();
    }
}
