package com.fnvls.postservice.impl.service;

import com.fnvls.postservice.api.dto.output.LikesOutputDto;
import com.fnvls.postservice.api.dto.output.UserOutputDto;
import com.fnvls.postservice.api.response.BaseResponse;
import com.fnvls.postservice.api.service.PostLikeService;
import com.fnvls.postservice.data.Post;
import com.fnvls.postservice.data.PostLike;
import com.fnvls.postservice.impl.exception.PostNotFoundException;
import com.fnvls.postservice.impl.repository.PostLikeRepository;
import com.fnvls.postservice.impl.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public boolean like(Long id, String postId) {
        Optional<Post> tempPost = postRepository.findById(postId);
        if(tempPost.isEmpty()) throw new PostNotFoundException();

        Post post = tempPost.get();

        Optional<PostLike> tempLike = postLikeRepository.findById(postId);

        PostLike postLike;
        if (tempLike.isEmpty()) {
            postLike = PostLike.builder().id(postId).usersId(new ArrayList<Long>()).build();
            postLike.getUsersId().add(id);
            post.setLikes_count(post.getLikes_count() + 1);
            postLikeRepository.save(postLike);
            postRepository.save(post);
            return true;
        } else {
            postLike = tempLike.get();
            boolean liked = postLike.getUsersId().contains(id);
            if(liked) {
                postLike.getUsersId().remove(id);
                post.setLikes_count(post.getLikes_count() - 1);
                postLikeRepository.save(postLike);
                postRepository.save(post);
                return false;
            } else {
                postLike.getUsersId().add(id);
                post.setLikes_count(post.getLikes_count() + 1);
                postLikeRepository.save(postLike);
                postRepository.save(post);
                return true;
            }
        }
    }

    @Override
    public LikesOutputDto getLikes(String id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()) throw new PostNotFoundException();

        Optional<PostLike> tempLike = postLikeRepository.findById(id);
        if(tempLike.isEmpty()) return null;

        PostLike like = tempLike.get();

        LikesOutputDto out = LikesOutputDto.builder()
                .id(id)
                .likesCount(post.get().getLikes_count())
                .likes(like.getUsersId())
                .build();

        return out;
    }
}
