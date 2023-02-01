package com.niugiaogiao.modules.wx.service;

import com.niugiaogiao.modules.wx.entity.dto.WXEventDTO;

public interface SubscriptionNumberService {

    void event(WXEventDTO wxEvent);
}
