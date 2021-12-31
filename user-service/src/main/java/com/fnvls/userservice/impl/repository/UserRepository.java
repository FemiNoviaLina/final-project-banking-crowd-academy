package com.fnvls.userservice.impl.repository;

import com.fnvls.userservice.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findDistinctUserByEmail(String email);
    Page<User> findByEnabled(Boolean enabled, Pageable pageable);
}
