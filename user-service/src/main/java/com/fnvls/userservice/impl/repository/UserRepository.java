package com.fnvls.userservice.impl.repository;

import com.fnvls.userservice.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findDistinctUserByEmail(String email);
    Page<User> findByEnabledAndRoleIn(Boolean enabled, List<String> roleList, Pageable pageable);
}
