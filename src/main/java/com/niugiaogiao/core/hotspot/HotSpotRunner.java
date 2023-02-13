package com.niugiaogiao.core.hotspot;

import com.alibaba.fastjson2.JSONObject;
import com.niugiaogiao.core.parse.HotSpotParserRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotSpotRunner {

    @Autowired
    List<HotSpot> hotSpotList;

    @Autowired
    HotSpotParserRunner hotSpotRunner;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void run() {
        for (HotSpot item : hotSpotList) {
            try {
                String response = item.downDataSource();
                HotSpotResponse hotSpotResponse = new HotSpotResponse(item.getPlantFlag(), JSONObject.parseObject(response));
                hotSpotRunner.parse(hotSpotResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
