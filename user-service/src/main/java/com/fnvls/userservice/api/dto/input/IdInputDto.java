package com.fnvls.userservice.api.dto.input;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IdInputDto {
    @NotBlank(message = "Id is not allowed to be null")
    private Long id;
}
