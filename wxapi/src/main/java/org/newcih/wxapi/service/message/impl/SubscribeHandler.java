package org.newcih.wxapi.service.message.impl;

import api.domain.eunm.WxMessageEvnet;
import org.newcih.wxapi.service.message.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author NEWCIH
 */
@Component
public class SubscribeHandler implements MessageHandler {

    @Override
    public WxMessageEvnet getEvent() {
        return WxMessageEvnet.EVENT_SUBSCRIBE;
    }

    @Override
    public Consumer<WxMessageEvnet> handle() {
        return wxMessageEvnet -> {

        };
    }

}
