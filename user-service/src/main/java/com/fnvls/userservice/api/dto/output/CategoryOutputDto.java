package com.fnvls.userservice.api.dto.output;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryOutputDto {
    private String id;
    private String name;
}
