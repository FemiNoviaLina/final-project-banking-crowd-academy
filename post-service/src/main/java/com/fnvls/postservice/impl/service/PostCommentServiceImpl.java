package com.fnvls.postservice.impl.service;

import com.fnvls.postservice.api.dto.input.CommentInputDto;
import com.fnvls.postservice.api.dto.output.CommentOutputDto;
import com.fnvls.postservice.api.dto.output.PostCommentOutputDto;
import com.fnvls.postservice.api.service.PostCommentService;
import com.fnvls.postservice.data.Comment;
import com.fnvls.postservice.data.Post;
import com.fnvls.postservice.data.PostComment;
import com.fnvls.postservice.impl.repository.PostCommentRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class PostCommentServiceImpl implements PostCommentService {
    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentOutputDto createComment(String sub, String id, CommentInputDto input) {
        Optional <PostComment> tempPostComment = postCommentRepository.findById(id);

        PostComment postComment;
        if(tempPostComment.isEmpty())
            postComment = PostComment.builder()
                    .id(id)
                    .comments(new ArrayList<>())
                    .build();
        else postComment = tempPostComment.get();

        Comment comment = Comment.builder()
                .id(new ObjectId().toString())
                .userId(Long.parseLong(sub))
                .comment(input.getComment())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        postComment.getComments().add(comment);
        postCommentRepository.save(postComment);

        CommentOutputDto out = modelMapper.map(comment, CommentOutputDto.class);
        return out;
    }

    @Override
    public CommentOutputDto getComment(String postId, String id) {
        Optional <PostComment> tempPostComment = postCommentRepository.findById(postId);

        if(tempPostComment.isEmpty()) return null;

        PostComment postComment = tempPostComment.get();

        CommentOutputDto out = null;
        for (Comment comment: postComment.getComments()) {
            if(comment.getId().equals(id)) {
                out = modelMapper.map(comment, CommentOutputDto.class);
                break;
            }
        }
        return out;
    }

    @Override
    public void deleteComment(String postId, String id) {
        Optional <PostComment> tempPostComment = postCommentRepository.findById(postId);

        if(tempPostComment.isEmpty()) return;

        PostComment postComment = tempPostComment.get();

        for (Comment comment: postComment.getComments()) {
            if(comment.getId().equals(id)) {
                postComment.getComments().remove(comment);
                break;
            }
        }

        postCommentRepository.save(postComment);
    }

    @Override
    public PostCommentOutputDto getCommentsOnPost(String id) {
        Optional<PostComment> tempPostComment = postCommentRepository.findById(id);

        if(tempPostComment.isEmpty()) return null;

        PostComment postComment = tempPostComment.get();

        PostCommentOutputDto out = modelMapper.map(postComment, PostCommentOutputDto.class);
        return out;
    }
}
