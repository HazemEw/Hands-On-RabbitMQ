package com.example.logsconsume.services;

import com.example.logsconsume.enums.LogType;
import com.example.logsconsume.exceptions.MessageProcessingException;
import com.example.logsconsume.mappers.LogMapper;
import com.example.logsconsume.model.Log;
import com.example.logsconsume.repositores.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);


    private LogRepository logRepository;

    private LogMapper logMapper ;

    public LogService (LogRepository logRepo, LogMapper logMapper){
        this.logRepository = logRepo;
        this.logMapper = logMapper;

    }

    public void processMessage(String message, String severity) {
        try {
            if (message.length() < 50) {
                throw new MessageProcessingException("Message too short");
            }
            Log log = logMapper.mapMassageToLog(message, severity);
           // logRepository.save(log);
            System.out.println("Saved log to MongoDB: " + log.getMessage());
        }catch (MessageProcessingException e){
            logger.info(e.getMessage());
            throw e;
        }
    }



}
