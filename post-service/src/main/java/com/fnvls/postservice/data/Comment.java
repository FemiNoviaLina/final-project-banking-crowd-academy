package com.fnvls.postservice.data;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    private String id;
    private Long userId;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
}
