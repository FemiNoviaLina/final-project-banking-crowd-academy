package com.fnvls.userservice.impl.service;

import com.fnvls.userservice.api.dto.output.UserOutputDto;
import com.fnvls.userservice.api.service.UserService;
import com.fnvls.userservice.data.User;
import com.fnvls.userservice.impl.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserOutputDto> getUnapprovedUsers(Integer limit, Integer offset) {
        Pageable paging = PageRequest.of(offset, limit);
        Page<User> usersPage = userRepository.findByEnabled(Boolean.FALSE, paging);

        List<User> users = usersPage.getContent();
        List<UserOutputDto> out = new ArrayList<>();
        for (User user : users) {
            out.add(modelMapper.map(user, UserOutputDto.class));
        }

        return out;
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
