package com.fnvls.userservice.impl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnvls.userservice.api.dto.output.LogOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KafkaService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.topic}")
    private String topic;

    @Autowired
    private ObjectMapper objectMapper;


    public void produce(String message) {
        kafkaTemplate.send(topic, message);
    }

    public String buildMessage(String requestLine, Object input) throws JsonProcessingException {
        LogOutputDto out = LogOutputDto.builder()
                .serviceName("user-service")
                .requestLine(requestLine)
                .requestBody(input)
                .time(new Date())
                .build();

        return objectMapper.writeValueAsString(out);
    }

}
