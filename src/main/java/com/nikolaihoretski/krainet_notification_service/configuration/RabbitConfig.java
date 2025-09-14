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

    public static final String QUEUE_NAME_GET_EMAIL = "getemail.queue";
    private static final String ROUTING_KEY_GET_EMAIL = "getemail.routing.key";

    public static final String QUEUE_NAME_SEND_EMAIL = "sendemail.queue";
    public static final String EXCHANGE = "sendemail.exchange";
    public static final String ROUTING_KEY_SEND_EMAIL = "sendemail.routing.key";

    @Bean("getEmailQueue")
    public Queue getEmailQueue() {
        return new Queue(QUEUE_NAME_GET_EMAIL, true);
    }

    @Bean("sendEmailQueue")
    public Queue sendEmailQueue() {
        return new Queue(QUEUE_NAME_SEND_EMAIL, true);
    }

    @Bean
    public TopicExchange sendEmailExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingSendEmailQueue(@Qualifier("sendEmailQueue") Queue sendEmailQueue, TopicExchange sendEmailExchange) {
        return BindingBuilder.bind(sendEmailQueue)
                .to(sendEmailExchange)
                .with(ROUTING_KEY_SEND_EMAIL);
    }

    @Bean
    public Binding bindingGetEmailQueue(@Qualifier("getEmailQueue") Queue getEmailQueue, TopicExchange sendEmailExchange) {
        return BindingBuilder.bind(getEmailQueue)
                .to(sendEmailExchange)
                .with(ROUTING_KEY_GET_EMAIL);
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
