package com.fnvls.postservice.impl.repository;

import com.fnvls.postservice.data.PostLike;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends MongoRepository<PostLike, String> {
}
