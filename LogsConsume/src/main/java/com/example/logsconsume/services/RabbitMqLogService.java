package com.example.logsconsume.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;

import java.io.IOException;


@Service
public class RabbitMqLogService {
    private final LogService logService;

    public RabbitMqLogService(LogService logService) {
        this.logService = logService;
    }

    @RabbitListener(queues = "error_queue")
    public void consumeError(String message) {
        logService.processMessage(message, "error");
    }

    @RabbitListener(queues = "info_queue")
    public void consumeInfo(String message, Channel channel) throws IOException {
        logService.processMessage(message, "info");
    }

    @RabbitListener(queues = "warning_queue")
    public void consumeWarning(String message) {
        logService.processMessage(message, "warning");
    }

}