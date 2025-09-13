package com.nikolaihoretski.krainet_notification_service.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME_REQUEST_EMAILS = "requestemail.queue";
    private static final String ROUTING_KEY_REQUEST_EMAILS = "requestemail.routing.key.*";

    public static final String QUEUE_NAME_GET_EMAIL = "getemail.queue";
    private static final String ROUTING_KEY_GET_EMAIL = "getemail.routing.key.*";

    public static final String QUEUE_NAME_SEND_EMAIL = "sendemail.queue";
    public static final String EXCHANGE = "sendemail.exchange";
    public static final String ROUTING_KEY_SEND_EMAIL = "sendemail.routing.key.*";

    @Bean
    public Queue requestEmailQueue() {
        return new Queue(QUEUE_NAME_REQUEST_EMAILS, true);
    }

    @Bean
    public Queue getEmailQueue() {
        return new Queue(QUEUE_NAME_GET_EMAIL, true);
    }

    @Bean
    public Queue sendEmailQueue() {
        return new Queue(QUEUE_NAME_SEND_EMAIL, true);
    }

    @Bean
    public TopicExchange sendEmailExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Binding binding(@Qualifier("sendEmailQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SEND_EMAIL);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}
