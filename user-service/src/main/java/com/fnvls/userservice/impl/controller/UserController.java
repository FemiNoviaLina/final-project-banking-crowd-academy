package com.fnvls.userservice.impl.controller;

import com.fnvls.userservice.api.dto.input.IdInputDto;
import com.fnvls.userservice.api.dto.input.UserProfileInputDto;
import com.fnvls.userservice.api.dto.output.UserOutputDto;
import com.fnvls.userservice.api.dto.output.UserProfileOutputDto;
import com.fnvls.userservice.api.response.BaseResponse;
import com.fnvls.userservice.api.service.UserService;
import com.fnvls.userservice.impl.security.JwtUtil;
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

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("users/application")
    public ResponseEntity<BaseResponse<List<UserOutputDto>>> getUnapprovedUsers(
            @RequestHeader("role") String role,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        if(!role.equals("admin")) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Access to this resource is denied"), HttpStatus.FORBIDDEN);

        List<UserOutputDto> users = userService.getUnapprovedUsers(limit, offset);

        return new ResponseEntity(new BaseResponse<>(users), HttpStatus.OK);
    }

    @PatchMapping(value = "/users/approval", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> approveUsers(
            @RequestHeader("role") String role,
            @RequestBody List<IdInputDto> input
    ) {
        if(!role.equals("admin")) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Access to this resource is denied"), HttpStatus.FORBIDDEN);

        userService.approveUsersApplication(input);

        return new ResponseEntity(new BaseResponse(Boolean.TRUE, "Updated"), HttpStatus.OK);
    }

    @PutMapping("user/profile/{id}")
    public ResponseEntity<BaseResponse<UserProfileOutputDto>> updateUserProfile(
            @RequestHeader String role,
            @RequestHeader String sub,
            @PathVariable String id,
            UserProfileInputDto input
    ) {
        Long userId = Long.parseLong(id);

        if(!sub.equals(id.toString()) && !role.equals("admin"))
            return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Access to this resource is denied"), HttpStatus.FORBIDDEN);

        UserProfileOutputDto userProfile = userService.updateUserProfile(userId, input);

        if(userProfile == null) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "User not found"), HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity(new BaseResponse<>(userProfile), HttpStatus.OK);
    }

    @GetMapping("user/profile-pic/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = userService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println(ex);
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
