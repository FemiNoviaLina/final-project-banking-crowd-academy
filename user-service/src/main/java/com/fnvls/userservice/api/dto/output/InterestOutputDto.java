package com.fnvls.userservice.api.dto.output;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InterestOutputDto {
    private UserBasicInfoDto userBasicInfo;
    private List<CategoryOutputDto> categories;
}
