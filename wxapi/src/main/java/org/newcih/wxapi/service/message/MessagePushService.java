package org.newcih.wxapi.service.message;

import api.domain.WechatMessageEntity;
import com.google.gson.Gson;
import org.newcih.wxapi.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author newcih
 */
@Service
public class MessagePushService {

    private final RabbitTemplate rabbitTemplate;

    public MessagePushService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(WechatMessageEntity wechatMessageEntity) {
        String jsonData = new Gson().toJson(wechatMessageEntity);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, wechatMessageEntity.getAppid(), jsonData);
    }

}
