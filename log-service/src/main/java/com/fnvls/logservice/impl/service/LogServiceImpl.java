package com.fnvls.logservice.impl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnvls.logservice.api.service.LogService;
import com.fnvls.logservice.data.Log;
import com.fnvls.logservice.impl.repository.LogRepository;;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LogRepository logRepository;

    @Override
    @KafkaListener(topics = "${app.topic}")
    public void listenAndSave(String message) {
        log.info("New log record {}", message);
        Log log = convertToLog(message);
        logRepository.save(log);
    }

    @Override
    public List<Log> getLogs() {
        return logRepository.findAll();
    }

    public Log convertToLog(String payload) {
        try {
            if (payload != null) {
                return objectMapper.readValue(payload, Log.class);
            } else {
                log.error("payload variable is null.");
                throw new IllegalStateException("payload variable is null.");
            }
        } catch (IOException ioe) {
            log.error("payload variable parsing failed.");
            throw new IllegalStateException("payload variable parsing failed.", ioe);
        }
    }
}