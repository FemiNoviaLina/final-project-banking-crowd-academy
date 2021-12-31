package com.fnvls.userservice.impl.repository;

import com.fnvls.userservice.data.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findDistinctUserByEmail(String email);
}
