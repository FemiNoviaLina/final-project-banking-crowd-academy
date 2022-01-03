package com.fnvls.postservice.api.dto.output;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostOutputDto {
    private String id;

    private long userId;

    private String content;

    private List<String> images;

    private int likes_count;

    private int comments_count;

    private CategoryOutputDto categories;

    private Date createdAt;

    private Date updatedAt;
}
