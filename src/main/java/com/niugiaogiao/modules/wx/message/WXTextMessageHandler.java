package com.niugiaogiao.modules.wx.message;

import cn.hutool.core.util.XmlUtil;
import com.niugiaogiao.utils.HotSpotUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;
import java.util.Date;

@Service
@AllArgsConstructor
class WXTextMessageHandler implements WXMessageHandler {

    private final HotSpotUtil hotSpotUtil;

    private final String helpText = "1.知乎\n2.微博\n3.新闻联播 [未开放]";

    @Override
    public boolean isHandler(String messageType) {
        return true;
    }

    @Override
    public String handler(Document document) {
        TextHandlerReceiveDTO textHandlerReceive = getTextHandlerReceive(document);
        return chooseResponse(textHandlerReceive);
    }

    private String chooseResponse(TextHandlerReceiveDTO textHandlerReceive) {
        String content = textHandlerReceive.getContent();
        switch (content) {
            case "1":
                return getResponse(hotSpotUtil.wxHotSpotZhiHu(), textHandlerReceive);
            case "2":
                return getResponse(hotSpotUtil.wxHotSpotWeiBo(), textHandlerReceive);
            default:
                return getResponse(helpText, textHandlerReceive);
        }
    }

    private TextHandlerReceiveDTO getTextHandlerReceive(Document document) {
        TextHandlerReceiveDTO textHandlerReceiveDTO = new TextHandlerReceiveDTO();
        textHandlerReceiveDTO.toUserName = (String) XmlUtil.getByXPath("//xml/ToUserName", document, XPathConstants.STRING);
        textHandlerReceiveDTO.fromUserName = (String) XmlUtil.getByXPath("//xml/FromUserName", document, XPathConstants.STRING);
        textHandlerReceiveDTO.createTime = (String) XmlUtil.getByXPath("//xml/CreateTime", document, XPathConstants.STRING);
        textHandlerReceiveDTO.content = (String) XmlUtil.getByXPath("//xml/Content", document, XPathConstants.STRING);
        return textHandlerReceiveDTO;
    }

    private String getResponse(String hotSportBody, TextHandlerReceiveDTO handlerReceiveDTO) {
        String messageTemplate = "<xml>\n" +
                "  <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[fromUser]]></FromUserName>\n" +
                "  <CreateTime>%time%</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[%msg%]]></Content>\n" +
                "</xml>";
        String xmlBody = messageTemplate.replace("toUser", handlerReceiveDTO.getFromUserName());
        xmlBody = xmlBody.replace("fromUser", handlerReceiveDTO.getToUserName());
        xmlBody = xmlBody.replace("%time%", String.valueOf(new Date().getTime()));
        xmlBody = xmlBody.replace("%msg%", hotSportBody);
        return xmlBody;
    }

    @Data
    private static class TextHandlerReceiveDTO {
        private String toUserName;
        private String fromUserName;
        private String createTime;
        private String content;
    }
}
