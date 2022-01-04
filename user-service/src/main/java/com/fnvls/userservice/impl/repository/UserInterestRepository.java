package com.fnvls.userservice.impl.repository;

import com.fnvls.userservice.data.InterestId;
import com.fnvls.userservice.data.UserInterest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInterestRepository extends CrudRepository<UserInterest, InterestId> {
    List<UserInterest> findByUserId(Long userId);
}
