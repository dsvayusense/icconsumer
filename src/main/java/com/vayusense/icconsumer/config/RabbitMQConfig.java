package com.vayusense.icconsumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class RabbitMQConfig implements RabbitListenerConfigurer {
    @Value("${app1.queue.name}")
    private String queueName;

    @Value("${app1.exchange.name}")
    private String exchange;

    @Value("${app1.routing.key}")
    private String routingkey;

    @Value("${app2.queue.name}")
    private String queueName2;

    @Value("${app2.exchange.name}")
    private String exchange2;

    @Value("${app2.routing.key}")
    private String routingkey2;

    @Value("${app3.queue.name}")
    private String queueName3;

    @Value("${app3.exchange.name}")
    private String exchange3;

    @Value("${app3.routing.key}")
    private String routingkey3;

    @Bean
    public Queue getApp1Queue() {
        return new Queue(queueName, false);
    }

    @Bean
    public DirectExchange getApp1exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding app1binding() {
        return BindingBuilder.bind(getApp1Queue()).to(getApp1exchange()).with(routingkey);
    }
    @Bean
    public Queue getApp2Queue() {
        return new Queue(queueName2, false);
    }

    @Bean
    public DirectExchange getApp2exchange() {
        return new DirectExchange(exchange2);
    }

    @Bean
    public Binding app2binding() {
        return BindingBuilder.bind(getApp2Queue()).to(getApp2exchange()).with(routingkey2);
    }

    @Bean
    public Queue getApp3Queue() {
        return new Queue(queueName3, false);
    }

    @Bean
    public DirectExchange getApp3exchange() {
        return new DirectExchange(exchange3);
    }

    @Bean
    public Binding app3binding() {
        return BindingBuilder.bind(getApp3Queue()).to(getApp3exchange()).with(routingkey3);
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }


    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> rabbitListenerContainerState(ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> rabbitListenerContainerlog(ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> rabbitListenerContainerUnit(ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(2);
        factory.setMaxConcurrentConsumers(4);
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());

    }


}
