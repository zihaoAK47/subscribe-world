package com.niugiaogiao.core.save;

import com.niugiaogiao.core.enums.Platform;

public interface HotSpotSave {
    <T> void save(Platform platform, T parseResult);
}
