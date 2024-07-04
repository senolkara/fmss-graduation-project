package com.senolkarakurt.customerservice.config;

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
public class RabbitCustomerConfig {

    @Value(value = "${notification.customer.queue}")
    private String notificationCustomerQueue;

    @Value(value = "${notification.customer.exchange}")
    private String customerExchange;

    @Value(value = "${notification.customer.routingkey}")
    private String customerRoutingKey;

    @Bean
    public Queue notificationCustomerQueue() {
        return new Queue(notificationCustomerQueue);
    }

    @Bean
    public FanoutExchange fanoutCustomerExchange() {
        return new FanoutExchange(customerExchange);
    }

    @Bean
    public Binding customerBinding(Queue notificationCustomerQueue, FanoutExchange fanoutCustomerExchange) {
        return BindingBuilder
                .bind(notificationCustomerQueue)
                .to(fanoutCustomerExchange)
                //.with(customerRoutingKey)
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
