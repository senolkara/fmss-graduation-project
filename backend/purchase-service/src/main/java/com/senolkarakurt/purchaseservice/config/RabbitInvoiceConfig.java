package com.senolkarakurt.purchaseservice.config;

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
public class RabbitInvoiceConfig {

    @Value(value = "${notification.invoice.queue}")
    private String notificationInvoiceQueue;

    @Value(value = "${notification.invoice.exchange}")
    private String invoiceExchange;

    @Value(value = "${notification.invoice.routingkey}")
    private String invoiceRoutingKey;

    @Bean
    public Queue notificationInvoiceQueue() {
        return new Queue(notificationInvoiceQueue);
    }

    @Bean
    public FanoutExchange fanoutInvoiceExchange() {
        return new FanoutExchange(invoiceExchange);
    }

    @Bean
    public Binding invoiceBinding(@Qualifier("notificationInvoiceQueue") Queue notificationInvoiceQueue,
                                  @Qualifier("fanoutInvoiceExchange") FanoutExchange fanoutInvoiceExchange) {
        return BindingBuilder
                .bind(notificationInvoiceQueue)
                .to(fanoutInvoiceExchange)
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
