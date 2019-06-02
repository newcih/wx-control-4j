package org.newcih.wxapi.web;

import api.domain.WxMessageEntity;
import org.newcih.wxapi.domain.WxDataInfo;
import org.newcih.wxapi.service.impl.CommonServiceImpl;
import org.newcih.wxapi.service.message.MessagePushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static org.newcih.wxapi.util.XmlUtil.xmlToMap;

/**
 * @author NEWCIH
 */
@RestController
public class WxMessageAccept extends BaseWxController {

    private static final Logger log = LoggerFactory.getLogger(WxMessageAccept.class);
    private static final Marker MESSAGE_MARKER = MarkerFactory.getMarker("MESSAGE");

    private final CommonServiceImpl commonService;
    private final MessagePushService messagePushService;

    public WxMessageAccept(CommonServiceImpl commonService, MessagePushService messagePushService) {
        this.commonService = commonService;
        this.messagePushService = messagePushService;
    }

    /**
     * 接收微信服务器地址推送的事件
     */
    @PostMapping(value = "messageAccept")
    public void messageAccept(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> messageData = xmlToMap(request);
        WxDataInfo wxDataInfo = commonService.getWxInfoByWechatId(messageData.get("ToUserName"));
        WxMessageEntity messageParams = new WxMessageEntity(messageData, wxDataInfo.getWxInfo());
        log.info(MESSAGE_MARKER, "接受微信消息{}", messageParams);

        try {
            messagePushService.sendMessage(messageParams);
        } finally {
            try {
                PrintWriter writer = response.getWriter();
                writer.print("success");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                log.error("回复微信服务器消息推送发生异常", e);
            }
        }

    }

    /**
     * 服务器地址验证代码
     *
     * @param request
     * @return
     */
    @GetMapping("messageAccept")
    public String validToken(HttpServletRequest request) {
        return request.getParameter("echostr");
    }

}
