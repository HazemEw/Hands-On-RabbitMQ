package com.example.logsconsume.model;

import com.example.logsconsume.enums.LogType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logs")
public class Log {
    @Id
    private String id;

    @JsonProperty("log_message")
    private String message;

    private LogType type;  // Change from String to LogType

    public Log() {
    }

    public Log(String id, String logMessage, LogType logType) { // Update constructor
        this.id = id;
        this.message = logMessage;
        this.type = logType;
    }

    public LogType getType(LogType logType) { // Update return type
        return type;
    }

    public void setType(LogType type) { // Update parameter type
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
