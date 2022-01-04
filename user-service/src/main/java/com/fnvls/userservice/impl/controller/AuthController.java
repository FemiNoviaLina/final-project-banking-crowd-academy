package com.fnvls.userservice.impl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fnvls.userservice.api.dto.input.LoginInputDto;
import com.fnvls.userservice.api.dto.input.RegisterInputDto;
import com.fnvls.userservice.api.dto.output.AuthUserOutputDto;
import com.fnvls.userservice.api.response.BaseResponse;
import com.fnvls.userservice.api.service.AuthService;
import com.fnvls.userservice.impl.security.JwtUtil;
import com.fnvls.userservice.impl.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/user")
    public ResponseEntity<BaseResponse<AuthUserOutputDto>> register(@Valid @RequestBody RegisterInputDto input)  {
        try {
            Object logUserRequest = RegisterInputDto.builder()
                    .email(input.getEmail())
                    .password("-")
                    .name(input.getName())
                    .role(input.getRole())
                    .build();
            kafkaService.produce(kafkaService.buildMessage("POST /auth/user", logUserRequest));
        } catch (JsonProcessingException e) {
            log.info("Catch Json Processing Exception {}", e);
        }

        AuthUserOutputDto user = authService.register(input);

        if(user == null) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Email already registered"), HttpStatus.CONFLICT);
        return new ResponseEntity(new BaseResponse<>(user), HttpStatus.CREATED);
    }

    @PostMapping("/session")
    public ResponseEntity<BaseResponse<AuthUserOutputDto>> login(@Valid @RequestBody LoginInputDto input) {
        try {
            Object logUserRequest = LoginInputDto.builder().email(input.getEmail()).password("-").build();
            kafkaService.produce(kafkaService.buildMessage("POST /auth/user", logUserRequest));
            AuthUserOutputDto user = authService.login(input);
            return new ResponseEntity(new BaseResponse<>(user), HttpStatus.OK);
        } catch (LockedException e) {
            return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Account has not been enabled"), HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException e) {
            return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Invalid email or password"), HttpStatus.UNAUTHORIZED);
        } catch (JsonProcessingException e) {
            log.info("Catch Json Processing Exception {}", e);
            return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}