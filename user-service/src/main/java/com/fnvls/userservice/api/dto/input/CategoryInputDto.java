package com.fnvls.userservice.api.dto.input;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryInputDto {
    private String id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
