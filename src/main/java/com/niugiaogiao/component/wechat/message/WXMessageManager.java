package com.niugiaogiao.component.wechat.message;

import cn.hutool.core.util.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;
import java.util.List;

@Component
public final class WXMessageManager {

    private final List<WXMessageHandler> wxMessageHandler;

    private WXMessageManager(@Autowired List<WXMessageHandler> wxMessageHandler) {
        this.wxMessageHandler = wxMessageHandler;
    }

    public String handleMessage(String xmlData) {
        Document document = XmlUtil.parseXml(xmlData);
        String messageType = getMessageType(document);
        for (WXMessageHandler itemHandler : wxMessageHandler) {
            if (itemHandler.isHandler(messageType)) {
                return itemHandler.handler(document);
            }
        }

        return null;
    }

    private String getMessageType(Document document) {
        return (String) XmlUtil.getByXPath("//xml/MsgType", document, XPathConstants.STRING);
    }
}
