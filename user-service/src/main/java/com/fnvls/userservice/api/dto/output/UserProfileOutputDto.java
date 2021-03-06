package com.fnvls.userservice.api.dto.output;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProfileOutputDto {
    private UserBasicInfoDto userBasicInfo;
    private String profilePic;
    private String dateOfBirth;
    private String phoneNumber;
    private String address;
    private Date createdAt;
    private Date updatedAt;
}
