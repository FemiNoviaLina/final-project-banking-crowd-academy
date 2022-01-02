package com.fnvls.userservice.api.dto.input;

import com.fnvls.userservice.api.validation.EnumValidator;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterInputDto {
    @NotBlank(message = "Email is not allowed to be null")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email")
    private String email;

    private String name;

    @NotBlank(message = "Password is not allowed to be null")
    private String password;

    @EnumValidator(
            enumClass = Role.class,
            message = "This error is coming from the enum class"
    )
    private String role;
}
