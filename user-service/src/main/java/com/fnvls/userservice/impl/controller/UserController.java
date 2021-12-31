package com.fnvls.userservice.impl.controller;

import com.fnvls.userservice.api.dto.output.UserOutputDto;
import com.fnvls.userservice.api.response.BaseResponse;
import com.fnvls.userservice.api.service.UserService;
import com.fnvls.userservice.impl.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("users/application")
    public ResponseEntity<BaseResponse<List<UserOutputDto>>> getUnapprovedUsers(
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        System.out.println("here");

        List<UserOutputDto> users = userService.getUnapprovedUsers(limit, offset);

        return new ResponseEntity(new BaseResponse<>(users), HttpStatus.OK);
    }
}
