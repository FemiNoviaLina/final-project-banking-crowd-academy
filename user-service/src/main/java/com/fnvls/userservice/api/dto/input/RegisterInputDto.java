package com.fnvls.userservice.api.dto.input;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterInputDto {
    @NotBlank(message = "Email is not allowed to be null")
    private String email;

    private String name;

    @NotBlank(message = "Password is not allowed to be null")
    private String password;

    private String role;
}
