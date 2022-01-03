package com.fnvls.userservice.impl.service;

import com.fnvls.userservice.api.dto.input.LoginInputDto;
import com.fnvls.userservice.api.dto.input.RegisterInputDto;
import com.fnvls.userservice.api.dto.output.UserOutputDto;
import com.fnvls.userservice.api.service.AuthService;
import com.fnvls.userservice.data.User;
import com.fnvls.userservice.impl.repository.UserRepository;
import com.fnvls.userservice.impl.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserOutputDto register(RegisterInputDto input) {
        User temp = userRepository.findDistinctUserByEmail(input.getEmail());

        if(temp != null) return null;

        User user = User.builder()
                .email(input.getEmail())
                .name(input.getName())
                .role(input.getRole())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();

        user.setEnabled(false);

        this.userRepository.save(user);

        UserOutputDto out = modelMapper.map(user, UserOutputDto.class);
        out.setToken(jwtUtil.generateToken(user.getId().toString(), user.getEmail(), user.getRole()));

        return out;
    }

    @Override
    public UserOutputDto login(LoginInputDto input) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();

            UserOutputDto out = modelMapper.map(user, UserOutputDto.class);
            out.setToken(jwtUtil.generateToken(user.getId().toString(), user.getEmail(), user.getRole()));
            return out;
        } catch (BadCredentialsException e) {
            throw e;
        } catch (LockedException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
