package com.fnvls.postservice.api.dto.output;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryOutputDto {
    private String id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
