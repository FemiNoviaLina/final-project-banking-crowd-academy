package com.fnvls.postservice.api.dto.output;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCategoryOutputDto {
    private String id;
    private String name;
}
