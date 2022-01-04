package com.fnvls.logservice.impl.controller;

import com.fnvls.logservice.api.dto.input.LogInputDto;
import com.fnvls.logservice.api.service.LogService;
import com.fnvls.logservice.data.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("/logs")
    public ResponseEntity<Log> getLog(@RequestBody LogInputDto input) {
        return new ResponseEntity(logService.getLogs(), HttpStatus.OK);
    }
}
