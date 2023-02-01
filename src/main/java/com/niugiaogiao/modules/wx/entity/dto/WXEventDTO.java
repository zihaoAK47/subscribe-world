package com.niugiaogiao.modules.wx.entity.dto;

import lombok.Data;

@Data
public class WXEventDTO {
    private String signature;
    private Long echoStr;
    private String timestamp;
    private String nonce;
}
