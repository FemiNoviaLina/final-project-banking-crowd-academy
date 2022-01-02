package com.fnvls.userservice.api.service;

import com.fnvls.userservice.api.dto.input.IdInputDto;
import com.fnvls.userservice.api.dto.input.UserProfileInputDto;
import com.fnvls.userservice.api.dto.output.UserOutputDto;
import com.fnvls.userservice.api.dto.output.UserProfileOutputDto;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<UserOutputDto> getUnapprovedUsers(Integer limit, Integer offset);
    public void approveUsersApplication(List<IdInputDto> input);
    public UserProfileOutputDto updateUserProfile(Long id, UserProfileInputDto input);
    public Resource loadFileAsResource(String fileName);
}
