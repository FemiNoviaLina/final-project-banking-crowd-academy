package com.fnvls.userservice.api.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProfileInputDto {

    private MultipartFile profilePic;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @Pattern(regexp = "^(\\+62|62|0)8[1-9][0-9]{6,9}$")
    private String phoneNumber;

    private String address;
}
