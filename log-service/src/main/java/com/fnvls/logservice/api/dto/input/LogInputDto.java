package com.fnvls.logservice.api.dto.input;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LogInputDto {
    private String serviceName;

    private String requestLine;

    private Object requestBody;

    private Date time;
}
