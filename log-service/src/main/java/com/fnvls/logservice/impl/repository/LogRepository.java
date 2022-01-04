package com.fnvls.logservice.impl.repository;

import com.fnvls.logservice.data.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, String> {
}
