package com.senolkarakurt.advertisementservice.config;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitAdvertisementConfig {

    @Value(value = "${notification.advertisement.queue}")
    private String notificationAdvertisementQueue;

    @Value(value = "${notification.advertisement.exchange}")
    private String advertisementExchange;

    @Value(value = "${notification.advertisement.routingkey}")
    private String advertisementRoutingKey;

    @Bean
    public Queue notificationAdvertisementQueue() {
        return new Queue(notificationAdvertisementQueue);
    }

    @Bean
    public FanoutExchange fanoutAdvertisementExchange() {
        return new FanoutExchange(advertisementExchange);
    }

    @Bean
    public Binding advertisementBinding(Queue notificationAdvertisementQueue, FanoutExchange fanoutAdvertisementExchange) {
        return BindingBuilder
                .bind(notificationAdvertisementQueue)
                .to(fanoutAdvertisementExchange)
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
