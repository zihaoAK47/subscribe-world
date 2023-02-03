package com.niugiaogiao.component.wechat.message;

import cn.hutool.core.util.XmlUtil;
import com.niugiaogiao.core.save.HotSpotCacheKey;
import com.niugiaogiao.utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;
import java.util.Date;

@Service
@AllArgsConstructor
class WXTextMessageHandler implements WXMessageHandler {

    private final RedisUtil redisUtil;

    private final String helpText = "a.知乎\nb.微博\nc.新闻联播 [未开放]";

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
            case "a":
                return responseImage(HotSpotCacheKey.HOT_SPOT_IMAGES_ZHI_HU, textHandlerReceive);
            case "b":
                return responseImage(HotSpotCacheKey.HOT_SPOT_IMAGES_WEI_BO, textHandlerReceive);
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

    private String responseImage(String redisKey, TextHandlerReceiveDTO handlerReceiveDTO) {
        String mediaId = (String) redisUtil.get(redisKey);
        if (StringUtils.isEmpty(mediaId)) {
            return null;
        }

        String messTemplate = "<xml>\n" +
                "  <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[fromUser]]></FromUserName>\n" +
                "  <CreateTime>%time%</CreateTime>\n" +
                "  <MsgType><![CDATA[image]]></MsgType>\n" +
                "  <Image>\n" +
                "    <MediaId><![CDATA[media_id]]></MediaId>\n" +
                "  </Image>\n" +
                "</xml>\n";
        String xmlBody = messTemplate.replace("toUser", handlerReceiveDTO.getFromUserName());
        xmlBody = xmlBody.replace("fromUser", handlerReceiveDTO.getToUserName());
        xmlBody = xmlBody.replace("%time%", String.valueOf(new Date().getTime()));
        xmlBody = xmlBody.replace("media_id", mediaId);
        return xmlBody;
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
