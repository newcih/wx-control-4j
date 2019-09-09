package org.newcih.wxapi.service.message.impl;

import api.domain.eunm.WechatMessageEventEnum;
import org.newcih.wxapi.service.message.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author NEWCIH
 */
@Component
public class SubscribeHandler implements MessageHandler {

    @Override
    public WechatMessageEventEnum getEvent() {
        return WechatMessageEventEnum.EVENT_SUBSCRIBE;
    }

    @Override
    public Consumer<WechatMessageEventEnum> handle() {
        return wxMessageEvnet -> {

        };
    }

}
