package com.fnvls.logservice.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document("logs")
public class Log {
    @Id
    private String id;

    private String serviceName;

    private String requestLine;

    private Object requestBody;

    private Date time;
}
