package com.niugiaogiao.core.parse;

import com.niugiaogiao.core.hotspot.HotSpotResponse;
import com.niugiaogiao.core.save.HotSpotSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class HotSpotParserRunner {

    @Autowired
    List<HotSpotParse> hotSpotParses;

    @Autowired
    List<HotSpotSave> hotSpotSave;


    public void parse(HotSpotResponse hotSpotData) {
        if (StringUtils.isEmpty(hotSpotData))
            return;

        for (HotSpotParse item : hotSpotParses) {
            if (!item.isParse(hotSpotData))
                continue;

            List<BaseParseResult> parse = item.parse(hotSpotData.getHostSpotVal());
            if (ObjectUtils.isEmpty(parse) || parse.isEmpty())
                continue;

            hotSpotSave.forEach(saveComponent -> saveComponent.save(hotSpotData.getPlantFlag(), parse));
        }
    }
}
