package com.niugiaogiao.core.hotspot;

import com.niugiaogiao.core.enums.Platform;

public interface HotSpot {

    /**
     * 获取平台标识
     * @return 返回平台标识信息
     */
    Platform getPlantFlag();

    String downDataSource();
}
