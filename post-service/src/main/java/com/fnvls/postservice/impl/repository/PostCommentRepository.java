package com.fnvls.postservice.impl.repository;

import com.fnvls.postservice.data.PostComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends MongoRepository<PostComment, String> {
}
