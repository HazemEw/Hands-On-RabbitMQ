package com.example.logsconsume.mappers;

import com.example.logsconsume.enums.LogType;
import com.example.logsconsume.model.Log;
import org.springframework.stereotype.Service;

@Service
public class LogMapper {

    public Log mapMassageToLog(String massage, String severity) {
        Log log = new Log();
        log.setMessage(massage);
        log.getType(LogType.valueOf(severity.toUpperCase()));
        return log;

    }
}
