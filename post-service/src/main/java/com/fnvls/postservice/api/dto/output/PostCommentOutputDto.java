package com.fnvls.postservice.api.dto.output;

import com.fnvls.postservice.data.Comment;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCommentOutputDto {
    private String id;
    private List<Comment> comments;
}
