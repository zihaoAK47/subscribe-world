package com.niugiaogiao.modules.hotspot.controller;


import com.niugiaogiao.modules.hotspot.vo.HotSpotWeiVO;
import com.niugiaogiao.modules.hotspot.vo.HotSpotZhiHuVO;
import com.niugiaogiao.utils.HotSpotUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/hotSpot")
class HotSpotController {

    @Autowired
    private final HotSpotUtil hotSpotUtil;

    @GetMapping("/topic_1")
    public List<HotSpotZhiHuVO> hotSpotZhiHu() {
        return hotSpotUtil.hotSpotZhiHu();
    }

    @GetMapping("/topic_2")
    public List<HotSpotWeiVO> hotSpotWeiBo() {
        return hotSpotUtil.hotSpotWeiBo();
    }
}
