package com.example.logsconsume.configurations;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitMqConfig {

    public static final String ERROR_QUEUE = "error_queue";
    public static final String INFO_QUEUE = "info_queue";
    public static final String WARNING_QUEUE = "warning_queue";
    public static final String DLQ_NAME = "logs_dlq";
    public static final String LOGS_EXCHANGE = "logs_exchange";



    @Bean
    public DirectExchange logsExchange() {
        return new DirectExchange(LOGS_EXCHANGE, true, false); // durable = true
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("deadLetterExchange");
    }

    @Bean
    public Queue errorQueue() {
        return QueueBuilder.durable(ERROR_QUEUE)
                .withArgument("x-dead-letter-exchange", "deadLetterExchange")
                .withArgument("x-dead-letter-routing-key", DLQ_NAME)
                .build();
    }

    @Bean
    public Queue infoQueue() {
        return QueueBuilder.durable(INFO_QUEUE)
                .withArgument("x-dead-letter-exchange", "deadLetterExchange")
                .withArgument("x-dead-letter-routing-key", DLQ_NAME)
                .build();
    }

    @Bean
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE)
                .withArgument("x-dead-letter-exchange", "deadLetterExchange")
                .withArgument("x-dead-letter-routing-key", DLQ_NAME)
                .build();
    }

    @Bean
    public Queue dlq() {
        return QueueBuilder.durable(DLQ_NAME).build();
    }

    @Bean
    public Binding errorQueueBinding() {
        return BindingBuilder.bind(errorQueue())
                .to(logsExchange())
                .with("error");
    }

    @Bean
    public Binding infoQueueBinding() {
        return BindingBuilder.bind(infoQueue())
                .to(logsExchange())
                .with("info");
    }

    @Bean
    public Binding warningQueueBinding() {
        return BindingBuilder.bind(warningQueue())
                .to(logsExchange())
                .with("warning");
    }


    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(dlq())
                .to(deadLetterExchange())
                .with(DLQ_NAME);
    }




}
