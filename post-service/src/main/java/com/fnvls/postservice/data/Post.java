package com.fnvls.postservice.data;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document("post")
public class Post {
    @Id
    private String id;

    private long userId;

    private String content;

    private List<String> images;

    private int likes_count = 0;

    private int comments_count = 0;

    private List<String> categoriesId;

    private Date createdAt;

    private Date updatedAt;
}
