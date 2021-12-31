package com.fnvls.userservice.api.service;

import com.fnvls.userservice.api.dto.output.UserOutputDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<UserOutputDto> getUnapprovedUsers();
    public void approveUsersApplication(List<Long> input);
}
