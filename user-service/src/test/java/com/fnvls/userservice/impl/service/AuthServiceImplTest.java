package com.fnvls.userservice.impl.service;

import com.fnvls.userservice.api.dto.input.RegisterInputDto;
import com.fnvls.userservice.api.dto.output.AuthUserOutputDto;
import com.fnvls.userservice.data.User;
import com.fnvls.userservice.impl.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

class AuthServiceImplTest {
    private final EasyRandom EASY_RANDOM = new EasyRandom();
    private final ModelMapper modelMapper = new ModelMapper();
    private Long id;

    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserRepository repository;
    @Spy
    private ModelMapper mapper = new ModelMapper();
    @Spy
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        id = EASY_RANDOM.nextObject(Long.class);
    }

    @Test
    void registerWillReturnAuthOutput() {
        RegisterInputDto input = RegisterInputDto.builder().email("test@mail.com").password("password").role("learner").build();
        User user = User.builder().email(input.getEmail()).name(input.getName()).role(input.getRole()).password(input.getPassword()).build();

        var result = authService.register(input);

        when(repository.findDistinctUserByEmail("test@mail.com")).thenReturn(null);
        when(repository.save(user)).thenReturn(user);

        verify(repository, times(1)).findDistinctUserByEmail("test@mail.com");
        verify(repository, times(1)).save(user);
        verify(mapper, times(1)).map(user, AuthUserOutputDto.class);
    }

    @Test
    void login() {
    }
}