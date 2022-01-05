package com.fnvls.postservice.impl.controller;

import com.fnvls.postservice.api.dto.input.CommentInputDto;
import com.fnvls.postservice.api.dto.output.CommentOutputDto;
import com.fnvls.postservice.api.dto.output.PostCommentOutputDto;
import com.fnvls.postservice.api.response.BaseResponse;
import com.fnvls.postservice.api.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostCommentController {
    @Autowired
    private PostCommentService postCommentService;

    @PostMapping("{id}/comment")
    public ResponseEntity<BaseResponse<CommentOutputDto>> createComment(
            @RequestHeader String sub,
            @PathVariable String id,
            @RequestBody CommentInputDto input
    ) {
        CommentOutputDto comment = postCommentService.createComment(sub, id, input);

        return new ResponseEntity(new BaseResponse<>(comment), HttpStatus.CREATED);
    }

    @GetMapping("{postId}/comment/{id}")
    public ResponseEntity<BaseResponse<CommentOutputDto>> getComment(
            @PathVariable String postId,
            @PathVariable String id
    ) {
        CommentOutputDto comment = postCommentService.getComment(postId, id);

        if(comment == null) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Comment not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity(new BaseResponse<>(comment), HttpStatus.OK);
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<BaseResponse<PostCommentOutputDto>> getCommentsOnPost(@PathVariable String id) {
        PostCommentOutputDto postComment = postCommentService.getCommentsOnPost(id);

        if(postComment == null) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "No comment found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity(new BaseResponse<>(postComment), HttpStatus.OK);
    }

    @DeleteMapping("{postId}/comment/{id}")
    public ResponseEntity<BaseResponse<CommentOutputDto>> deleteComment(
            @PathVariable String postId,
            @PathVariable String id
    ) {
        postCommentService.deleteComment(postId, id);
        return new ResponseEntity(new BaseResponse<>(Boolean.TRUE, "Deleted"), HttpStatus.OK);
    }
}
