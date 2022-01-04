package com.fnvls.userservice.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse<T> {
    private Boolean success = Boolean.TRUE;
    private String message = "Operation success";
    private T data;

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}