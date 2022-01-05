package com.fnvls.userservice.impl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fnvls.userservice.api.dto.input.UserProfileInputDto;
import com.fnvls.userservice.api.dto.output.AuthUserOutputDto;
import com.fnvls.userservice.api.dto.output.InterestOutputDto;
import com.fnvls.userservice.api.dto.output.UserBasicInfoDto;
import com.fnvls.userservice.api.dto.output.UserProfileOutputDto;
import com.fnvls.userservice.api.response.BaseResponse;
import com.fnvls.userservice.api.service.InterestService;
import com.fnvls.userservice.api.service.UserService;
import com.fnvls.userservice.impl.security.JwtUtil;
import com.fnvls.userservice.impl.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private InterestService interestService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private KafkaService kafkaService;

    @GetMapping("/user/{id}")
    public ResponseEntity<BaseResponse<UserBasicInfoDto>> getUser(@PathVariable Long id) throws JsonProcessingException {
        kafkaService.produce(kafkaService.buildMessage("GET /user/" + id, null));
        UserBasicInfoDto user = userService.getUser(id);
        return new ResponseEntity(new BaseResponse<>(user), HttpStatus.OK);
    }

    @GetMapping("/users/application")
    public ResponseEntity<BaseResponse<List<AuthUserOutputDto>>> getUnapprovedUsers(
            @RequestHeader("role") String role,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) throws JsonProcessingException {
        kafkaService.produce(kafkaService.buildMessage("GET /users/application", new Object()));
        if(!role.equals("admin")) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Access to this resource is denied"), HttpStatus.FORBIDDEN);
        List<AuthUserOutputDto> users = userService.getUnapprovedUsers(limit, offset);
        return new ResponseEntity(new BaseResponse<>(users), HttpStatus.OK);
    }

    @PatchMapping(value = "/users/approval", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> approveUsers(
            @RequestHeader("role") String role,
            @RequestBody List<Long> input
    ) throws JsonProcessingException {
        kafkaService.produce(kafkaService.buildMessage("PATCH /users/approval", input));
        if(!role.equals("admin")) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Access to this resource is denied"), HttpStatus.FORBIDDEN);
        userService.approveUsersApplication(input);
        return new ResponseEntity(new BaseResponse(Boolean.TRUE, "Updated"), HttpStatus.OK);
    }

    @PutMapping("/user/profile/{id}")
    public ResponseEntity<BaseResponse<UserProfileOutputDto>> updateUserProfile(
            @RequestHeader String role,
            @RequestHeader String sub,
            @PathVariable String id,
            UserProfileInputDto input
    ) throws JsonProcessingException {
        kafkaService.produce(kafkaService.buildMessage("PUT /user/profile" + id, input));
        Long userId = Long.parseLong(id);
        if(!sub.equals(id.toString()) && !role.equals("admin"))
            return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Access to this resource is denied"), HttpStatus.FORBIDDEN);
        UserProfileOutputDto userProfile = userService.updateUserProfile(userId, input);
        if(userProfile == null) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "User not found"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(new BaseResponse<>(userProfile), HttpStatus.OK);
    }

    @GetMapping("/user/profile/{id}")
    public ResponseEntity<BaseResponse<UserProfileOutputDto>> getUserProfile(
            @RequestHeader String role,
            @RequestHeader String sub,
            @PathVariable String id
    ) throws JsonProcessingException {
        kafkaService.produce(kafkaService.buildMessage("GET /user/profile" + id, null));
        Long userId = Long.parseLong(id);
        if(!sub.equals(id.toString()) && !role.equals("admin"))
            return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Access to this resource is denied"), HttpStatus.FORBIDDEN);
        UserProfileOutputDto userProfile = userService.getUserProfile(userId);
        return new ResponseEntity(new BaseResponse<>(userProfile), HttpStatus.OK);
    }

    @GetMapping("/user/profile-pic/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        kafkaService.produce(kafkaService.buildMessage("GET /user/profile-pic/" + fileName, null));
        Resource resource = userService.loadFileAsResource(fileName);
        String contentType = null;
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) { contentType = "application/octet-stream"; }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/user/interest/{id}")
    public ResponseEntity<BaseResponse<InterestOutputDto>> getUserInterest(@PathVariable Long id) throws JsonProcessingException {
        kafkaService.produce(kafkaService.buildMessage("GET /user/interest" + id, null));
        InterestOutputDto interest = interestService.getInterest(id);
        return new ResponseEntity(new BaseResponse<>(interest), HttpStatus.OK);
    }

    @PostMapping("/user/interests")
    public ResponseEntity<BaseResponse<InterestOutputDto>> createUserInterest(
            @RequestHeader String sub,
            @RequestBody List<String> input) throws JsonProcessingException {
        kafkaService.produce(kafkaService.buildMessage("POST /user/interests", input));
        InterestOutputDto interest = interestService.createInterest(Long.parseLong(sub), input);
        return new ResponseEntity(new BaseResponse<>(interest), HttpStatus.OK);
    }
}