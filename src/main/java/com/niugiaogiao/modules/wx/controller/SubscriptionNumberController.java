package com.niugiaogiao.modules.wx.controller;

import com.niugiaogiao.conf.exception.ServiceException;
import com.niugiaogiao.modules.wx.entity.dto.WXEventDTO;
import com.niugiaogiao.utils.IgnoreResponseResult;
import com.niugiaogiao.utils.StreamUtil;
import com.niugiaogiao.utils.WXMessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 订阅号
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx")
class SubscriptionNumberController {
    private final WXMessageUtil wxMessageUtil;

    @GetMapping("/event")
    public IgnoreResponseResult<?> event(HttpServletRequest request) {
        WXEventDTO wxEventDTO = getRequestBody(request);
        return IgnoreResponseResult.toResult(wxEventDTO.getEchoStr());
    }

    @PostMapping("/event")
    public String eventPost(HttpServletRequest request) {
        String httpRequestContent = StreamUtil.getHttpRequestContent(request);
        return wxMessageUtil.handleMessage(httpRequestContent);
    }

    private WXEventDTO getRequestBody(HttpServletRequest request) {
        String nonce = request.getParameter("nonce");
        String echoStr = request.getParameter("echostr");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");

        if (StringUtils.isEmpty(nonce)
                || StringUtils.isEmpty(signature)
                || StringUtils.isEmpty(timestamp)
                || StringUtils.isEmpty(echoStr)) {
            ServiceException.parameterException();
        }

        WXEventDTO wxEventRO = new WXEventDTO();
        wxEventRO.setNonce(nonce);
        wxEventRO.setEchoStr(Long.parseLong(echoStr));
        wxEventRO.setSignature(signature);
        wxEventRO.setTimestamp(timestamp);
        return wxEventRO;
    }
}
