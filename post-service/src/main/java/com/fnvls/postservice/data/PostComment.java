package com.fnvls.postservice.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document("postComments")
public class PostComment {
    @Id
    private String id;

    private List<Comment> comments;
}
