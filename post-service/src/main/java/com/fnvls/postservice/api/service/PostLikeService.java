package com.fnvls.postservice.api.service;

import com.fnvls.postservice.api.dto.output.LikesOutputDto;

public interface PostLikeService {
    boolean like(Long id, String postId);

    LikesOutputDto getLikes(String id);
}
