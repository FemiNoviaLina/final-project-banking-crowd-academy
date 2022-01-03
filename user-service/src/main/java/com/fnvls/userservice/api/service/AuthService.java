package com.fnvls.userservice.api.service;

import com.fnvls.userservice.api.dto.input.LoginInputDto;
import com.fnvls.userservice.api.dto.input.RegisterInputDto;
import com.fnvls.userservice.api.dto.output.AuthUserOutputDto;

public interface AuthService {
    public AuthUserOutputDto register(RegisterInputDto input);
    public AuthUserOutputDto login(LoginInputDto input);
}
