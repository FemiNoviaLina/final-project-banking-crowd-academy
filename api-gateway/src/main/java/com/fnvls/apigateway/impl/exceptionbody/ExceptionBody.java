package com.fnvls.apigateway.impl.exceptionbody;

import lombok.Data;

@Data
public class ExceptionBody {

    final Boolean success = Boolean.FALSE;

    String message;

    public ExceptionBody(String message) {
        this.message = message;
    }
}
