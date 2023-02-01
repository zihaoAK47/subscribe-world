package com.niugiaogiao.modules.wx.message;


import org.w3c.dom.Document;

public interface WXMessageHandler {

    boolean isHandler(String messageType);

    String handler(Document document);
}
