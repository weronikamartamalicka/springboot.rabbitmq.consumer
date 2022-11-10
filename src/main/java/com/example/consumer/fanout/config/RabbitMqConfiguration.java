package com.example.consumer.fanout.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {


    @Bean
    public Queue queueA() {
        return new Queue("queue.A", true, false, false, null);
    }

    @Bean
    public Queue queueB() {
        return new Queue("queue.B", true, false, false, null);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("exchange.Fanout", true, false, null);
    }

    @Bean
    public Binding bindingA(Queue queueA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueA)
                .to(fanoutExchange);
    }

    @Bean
    public Binding bindingB(Queue queueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueB)
                .to(fanoutExchange);
    }

    @Bean
    MessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);

        return converter;
    }

    @Bean
    PooledChannelConnectionFactory connectionFactory() {
        ConnectionFactory rabbitConnectionFactory = new ConnectionFactory();
        rabbitConnectionFactory.setHost("localhost");

        return new PooledChannelConnectionFactory(rabbitConnectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(PooledChannelConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());

        return rabbitTemplate;
    }
}
