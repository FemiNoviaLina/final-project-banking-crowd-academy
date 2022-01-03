package com.fnvls.postservice.impl.repository;

import com.fnvls.postservice.data.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, String> {
    Page<Post> findByCategoriesIdIn(List<String> categoriesIdList, Pageable pageable);
}
