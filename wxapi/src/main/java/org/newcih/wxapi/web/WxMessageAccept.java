package org.newcih.wxapi.web;

import api.domain.WxMessageEntity;
import domain.WxInfo;
import org.newcih.wxapi.config.prop.WxProperties;
import org.newcih.wxapi.service.message.MessageHandler;
import org.newcih.wxapi.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.newcih.wxapi.util.XmlUtil.xmlToMap;

/**
 * @author NEWCIH
 */
@RestController
public class WxMessageAccept extends BaseWxController {

    private static final Logger log = LoggerFactory.getLogger(WxMessageAccept.class);

    private final WxProperties wxProperties;
    private final List<MessageHandler> messageHandlerList;

    public WxMessageAccept(WxProperties wxProperties, List<MessageHandler> messageHandlerList) {
        this.wxProperties = wxProperties;
        this.messageHandlerList = messageHandlerList;
    }

    /**
     * 接收微信服务器地址推送的事件
     */
    @PostMapping(value = "messageAccept")
    public void messageAccept(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> messageData = xmlToMap(request);
        WxInfo wxInfo = wxProperties.getWechatIdInnerMap().get(messageData.get("ToUserName"));
        WxMessageEntity messageParams = new WxMessageEntity(messageData, wxInfo);

        try {
            Optional<MessageHandler> messageHandlerOptional = messageHandlerList.parallelStream()
                    .filter(handler -> handler.getEvent().equals(messageParams.getWxMessageEvnet()))
                    .findFirst();
            if (messageHandlerOptional.isPresent()) {
                MessageHandler messageHandler = messageHandlerOptional.get();
                ThreadUtil.executor.execute(messageHandler::handle);
            } else {
                log.warn("当前微信平台{}消息找不到处理方法", messageParams);
            }
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
