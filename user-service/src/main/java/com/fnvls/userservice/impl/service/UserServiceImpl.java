package com.fnvls.userservice.impl.service;

import com.fnvls.userservice.api.dto.output.UserOutputDto;
import com.fnvls.userservice.api.service.UserService;
import com.fnvls.userservice.data.User;
import com.fnvls.userservice.impl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserOutputDto> getUnapprovedUsers() {
        return null;
    }

    @Override
    public void approveUsersApplication(List<Long> input) {

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findDistinctUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found!");
        }

        return user;
    }
}
