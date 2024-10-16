package com.example.logsconsume.exceptions;

public class MessageProcessingException extends RuntimeException{
    public MessageProcessingException(String message) {
        super(message);
    }
}
