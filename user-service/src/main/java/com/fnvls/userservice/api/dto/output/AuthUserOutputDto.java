package com.fnvls.userservice.api.dto.output;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserOutputDto {
    private Long id;
    private String email;
    private String token;
    private Date createdAt;
    private Date updatedAt;
}
