package com.fnvls.postservice.api.service;

import com.fnvls.postservice.api.dto.input.PostInputDto;
import com.fnvls.postservice.api.dto.output.PostOutputDto;
import org.springframework.core.io.Resource;

import java.util.List;

public interface PostService {
    PostOutputDto createPost(String id, PostInputDto input);

    List<PostOutputDto> getPosts(List<String> category, Integer limit, Integer offset);

    PostOutputDto getPost(String id);

    Resource getImagePost(String filename);
}
