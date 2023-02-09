package com.niugiaogiao.core.save;

import com.niugiaogiao.core.enums.Platform;

public final class HotSpotCacheKey {

    public static final String HOT_SPOT_FLAG = "hotSpot:";
    public static final String HOT_SPOT_IMAGE = "wx:material:";

    public static final String HOT_SPOT_ZHI_HU = HOT_SPOT_FLAG.concat(Platform.ZHI_HU.name());

    public static final String HOT_SPOT_WEI_BO = HOT_SPOT_FLAG.concat(Platform.WEI_BO.name());

    public static final String HOT_SPOT_IMAGES_ZHI_HU = HOT_SPOT_IMAGE.concat(Platform.ZHI_HU.name());
    public static final String HOT_SPOT_IMAGES_WEI_BO = HOT_SPOT_IMAGE.concat(Platform.WEI_BO.name());
    public static final String HOT_SPOT_IMAGES_BAI_DU = HOT_SPOT_IMAGE.concat(Platform.BAI_DU.name());

    private HotSpotCacheKey() {
    }
}
