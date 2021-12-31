package com.fnvls.userservice.api.service;

import com.fnvls.userservice.api.dto.input.LoginInputDto;
import com.fnvls.userservice.api.dto.input.RegisterInputDto;
import com.fnvls.userservice.api.dto.output.UserOutputDto;

public interface AuthService {
    public UserOutputDto register(RegisterInputDto input);
    public UserOutputDto login(LoginInputDto input);
}
