package com.example.logsconsume.repositores;

import com.example.logsconsume.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, String> {

}
