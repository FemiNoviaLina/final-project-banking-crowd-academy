package com.fnvls.postservice.api.dto.input;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostInputDto {
    private String content;

    private List<MultipartFile> images;

    private List<String> categoriesId;
}
