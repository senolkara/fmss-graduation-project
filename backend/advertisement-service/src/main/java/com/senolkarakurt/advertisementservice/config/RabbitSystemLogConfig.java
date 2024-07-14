package com.senolkarakurt.advertisementservice.config;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitSystemLogConfig {

    @Value(value = "${notification.system.log.queue}")
    private String notificationSystemLogQueue;

    @Value(value = "${notification.system.log.exchange}")
    private String systemLogExchange;

    @Value(value = "${notification.system.log.routingkey}")
    private String systemLogRoutingKey;

    @Bean
    public Queue notificationSystemLogQueue() {
        return new Queue(notificationSystemLogQueue);
    }

    @Bean
    public FanoutExchange fanoutSystemLogExchange() {
        return new FanoutExchange(systemLogExchange);
    }

    @Bean
    public Binding systemLogBinding(@Qualifier("notificationSystemLogQueue") Queue notificationSystemLogQueue,
                                    @Qualifier("fanoutSystemLogExchange") FanoutExchange fanoutSystemLogExchange) {
        return BindingBuilder
                .bind(notificationSystemLogQueue)
                .to(fanoutSystemLogExchange)
                ;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
