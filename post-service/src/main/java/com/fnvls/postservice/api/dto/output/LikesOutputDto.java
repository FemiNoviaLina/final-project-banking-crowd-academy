package com.fnvls.postservice.api.dto.output;

import com.fnvls.postservice.data.Comment;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikesOutputDto {
    private String id;
    private int likesCount;
    private List<Long> likes;
}
