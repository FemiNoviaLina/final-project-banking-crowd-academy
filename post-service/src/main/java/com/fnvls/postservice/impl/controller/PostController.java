package com.fnvls.postservice.impl.controller;

import com.fnvls.postservice.api.dto.input.PostInputDto;
import com.fnvls.postservice.api.dto.output.PostOutputDto;
import com.fnvls.postservice.api.response.BaseResponse;
import com.fnvls.postservice.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("post")
    public ResponseEntity<BaseResponse<PostOutputDto>> createPost(
            @RequestHeader String sub,
            PostInputDto input
    ) {
        PostOutputDto post = postService.createPost(sub, input);
        return new ResponseEntity(new BaseResponse<>(post), HttpStatus.CREATED);
    }

    @GetMapping("posts")
    public ResponseEntity<List<BaseResponse<PostOutputDto>>> getPosts(
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "") List<String> categoryId
    ) {
        List<PostOutputDto> posts = postService.getPosts(categoryId, limit, offset);

        return new ResponseEntity(new BaseResponse<>(posts), HttpStatus.OK);
    }

    @GetMapping("post/{id}")
    public ResponseEntity<BaseResponse<PostOutputDto>> getPost(@PathVariable String id) {
        PostOutputDto post = postService.getPost(id);

        if(post == null) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Post not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity(new BaseResponse<>(post), HttpStatus.OK);
    }

    @GetMapping("post/image/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = postService.getImagePost(fileName);

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
