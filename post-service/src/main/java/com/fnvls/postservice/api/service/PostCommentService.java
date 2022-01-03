package com.fnvls.postservice.api.service;

import com.fnvls.postservice.api.dto.input.CommentInputDto;
import com.fnvls.postservice.api.dto.output.CommentOutputDto;
import com.fnvls.postservice.api.dto.output.PostCommentOutputDto;

public interface PostCommentService {
    CommentOutputDto createComment(String sub, String id, CommentInputDto input);

    CommentOutputDto getComment(String postId, String id);

    void deleteComment(String postId, String id);

    PostCommentOutputDto getCommentsOnPost(String id);
}
