package com.fnvls.userservice.api.dto.output;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBasicInfoDto {
    private Long id;
    private String email;
    private String name;
    private String Role;
}
