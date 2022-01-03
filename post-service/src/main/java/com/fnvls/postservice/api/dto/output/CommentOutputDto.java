package com.fnvls.postservice.api.dto.output;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentOutputDto {
    private String id;
    private Long userId;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
}
