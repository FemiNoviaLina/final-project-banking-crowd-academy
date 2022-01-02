package com.fnvls.userservice.impl.repository;

import com.fnvls.userservice.data.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
}
