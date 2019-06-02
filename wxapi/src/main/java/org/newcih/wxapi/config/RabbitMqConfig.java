package org.newcih.wxapi.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "wx.message";

    @Bean
    public Exchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

}
