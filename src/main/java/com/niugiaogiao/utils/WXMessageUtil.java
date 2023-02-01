package com.niugiaogiao.utils;

import cn.hutool.core.util.XmlUtil;
import com.niugiaogiao.modules.wx.message.WXMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;
import java.util.List;

@Component
public final class WXMessageUtil {

    @Autowired
    private List<WXMessageHandler> wxMessageHandler;

    private WXMessageUtil() {
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
