package com.fnvls.postservice.impl.controller;

import com.fnvls.postservice.api.dto.output.LikesOutputDto;
import com.fnvls.postservice.api.response.BaseResponse;
import com.fnvls.postservice.api.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostLikesController {
    @Autowired
    private PostLikeService postLikeService;

    @PostMapping("/like/{id}")
    public ResponseEntity<BaseResponse> like(
            @RequestHeader String sub,
            @PathVariable String id
    ) {
        String message = postLikeService.like(Long.parseLong(sub), id) ? "Post liked successfully" : "Post unliked succesfully";

        return new ResponseEntity(new BaseResponse<>(Boolean.TRUE, message), HttpStatus.OK);
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<BaseResponse<LikesOutputDto>> getLikes(@PathVariable String id) {
        LikesOutputDto likes = postLikeService.getLikes(id);

        return new ResponseEntity(new BaseResponse<>(likes), HttpStatus.OK);
    }

}
