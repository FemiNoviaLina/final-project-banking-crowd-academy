package com.fnvls.userservice.api.dto.output;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LogOutputDto {
    private String serviceName;

    private String requestLine;

    private Object requestBody;

    private Date time;
}
