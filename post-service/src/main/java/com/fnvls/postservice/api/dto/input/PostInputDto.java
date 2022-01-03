package com.fnvls.postservice.api.dto.input;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostInputDto {
    private String content;

    private List<String> images;

    private List<String> categoriesId;
}
