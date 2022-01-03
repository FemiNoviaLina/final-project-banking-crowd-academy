package com.fnvls.userservice.api.service;

import com.fnvls.userservice.api.dto.input.UserProfileInputDto;
import com.fnvls.userservice.api.dto.output.AuthUserOutputDto;
import com.fnvls.userservice.api.dto.output.UserBasicInfoDto;
import com.fnvls.userservice.api.dto.output.UserProfileOutputDto;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<AuthUserOutputDto> getUnapprovedUsers(Integer limit, Integer offset);
    void approveUsersApplication(List<Long> input);
    UserProfileOutputDto updateUserProfile(Long id, UserProfileInputDto input);
    Resource loadFileAsResource(String fileName);
    UserProfileOutputDto getUserProfile(Long id);
    UserBasicInfoDto getUser(Long id);
}
