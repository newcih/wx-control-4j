package org.newcih.wxapi.service.message;

import api.domain.WxMessageEntity;
import com.google.gson.Gson;
import org.newcih.wxapi.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagePushService {

    private final RabbitTemplate rabbitTemplate;

    public MessagePushService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(WxMessageEntity wxMessageEntity) {
        String jsonData = new Gson().toJson(wxMessageEntity);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, wxMessageEntity.getAppid(), jsonData);
    }

}
