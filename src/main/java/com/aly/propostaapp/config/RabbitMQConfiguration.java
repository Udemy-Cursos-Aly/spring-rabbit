package com.aly.propostaapp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Value("${rabbit.mq.exchange}")
    private String exchange;

    @Value("${rabbit.mq.queue.pp.ms.credito}")
    private String queuePPMsCredito;

    @Value("${rabbit.mq.queue.pp.ms.notificacao}")
    private String queuePPMsNotificacao;

    @Value("${rabbit.mq.queue.pc.ms.proposta}")
    private String queuePCMsProposta;

    @Value("${rabbit.mq.queue.pc.ms.notificacao}")
    private String queuePCMsNotificacao;
    /*
      @TODO: Configuração de criação de filar e do admin
     */
    @Bean
    public Queue criarFilaPPMsCredito() {
        return QueueBuilder.durable(queuePPMsCredito).build();
    }

    @Bean
    public Queue criarFilaPPMsNotificacao() {
        return QueueBuilder.durable(queuePPMsNotificacao).build();
    }

    @Bean
    public Queue criarFilaPCMsProposta() {
        return QueueBuilder.durable(queuePCMsProposta).build();
    }

    @Bean
    public Queue criarFilaPCMsNotificacao() {
        return QueueBuilder.durable(queuePCMsNotificacao).build();
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange criarExchangePP() {
        return ExchangeBuilder.fanoutExchange(exchange).build();
    }

    @Bean
    public Binding criarBindingPPMsCredito() {
        return BindingBuilder.bind(criarFilaPPMsCredito()).to(criarExchangePP());
    }

    @Bean
    public Binding criarBindingPPMsNotificacao() {
        return BindingBuilder.bind(criarFilaPPMsNotificacao()).to(criarExchangePP());
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }
}
