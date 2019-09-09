package org.newcih.wxapi.service.message;

import api.domain.eunm.WechatMessageEventEnum;

import java.util.function.Consumer;

/**
 * 微信消息处理接口
 *
 * @author NEWCIH
 */
public interface MessageHandler {

    WechatMessageEventEnum getEvent();

    Consumer<WechatMessageEventEnum> handle();

}
