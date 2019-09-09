package org.newcih.wxapi.web;

import api.domain.WechatMessageEntity;
import com.qq.weixin.mp.aes.AesException;
import org.newcih.wxapi.service.impl.WechatServerMessageService;
import org.newcih.wxapi.service.message.MessagePushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author NEWCIH
 */
@RestController
@RequestMapping("wx")
public class WechatMessageAccept extends BaseWechatController {

    private static final Logger logger = LoggerFactory.getLogger(WechatMessageAccept.class);

    private WechatServerMessageService wechatServerMessageService;
    private MessagePushService messagePushService;

    public WechatMessageAccept(MessagePushService messagePushService, WechatServerMessageService wechatServerMessageService) {
        this.messagePushService = messagePushService;
        this.wechatServerMessageService = wechatServerMessageService;
    }

    /**
     * 接收微信服务器地址推送的事件
     */
    @PostMapping(value = "messageAccept")
    public void messageAccept(HttpServletRequest request, HttpServletResponse response) throws IOException, AesException {
        // 解密微信服务器消息
        WechatMessageEntity entity = wechatServerMessageService.decryptMessage(request);

        if (entity == null) {
            return;
        }

        // 推送到消息队列
        try {
            messagePushService.sendMessage(entity);
        } catch (Exception e) {
            logger.error("发送消息到消息队列发生异常", e);
        } finally {
            // 响应微信服务器
            try {
                PrintWriter writer = response.getWriter();
                writer.print("success");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                logger.error("回复微信服务器消息推送发生异常", e);
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
