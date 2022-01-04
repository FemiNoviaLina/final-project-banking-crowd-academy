package com.fnvls.userservice.impl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fnvls.userservice.api.dto.output.InterestOutputDto;
import com.fnvls.userservice.api.response.BaseResponse;
import com.fnvls.userservice.api.service.InterestService;
import com.fnvls.userservice.impl.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class InterestController {
    @Autowired
    private InterestService interestService;

    @Autowired
    private KafkaService kafkaService;

    @GetMapping("/user/interest/{id}")
    public ResponseEntity<BaseResponse<InterestOutputDto>> getUserInterest(@PathVariable Long id) {
        try {
            kafkaService.produce(kafkaService.buildMessage("GET /user/interest" + id, new Object()));
        } catch (JsonProcessingException e) {
            log.info("Catch Json Processing Exception {}", e);
        }

        InterestOutputDto interest = interestService.getInterest(id);

        if(interest == null) new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "User not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity(new BaseResponse<>(interest), HttpStatus.OK);
    }

    @PostMapping("/user/interests")
    public ResponseEntity<BaseResponse<InterestOutputDto>> approveUsers(
            @RequestHeader String sub,
            @RequestBody List<String> input) {
        try {
            kafkaService.produce(kafkaService.buildMessage("POST /user/interests", input));
        } catch (JsonProcessingException e) {
            log.info("Catch Json Processing Exception {}", e);
        }
        System.out.println(sub);

        InterestOutputDto interest = interestService.createInterest(Long.parseLong(sub), input);

        if(interest == null) new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "User not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity(new BaseResponse<>(interest), HttpStatus.OK);
    }
}
