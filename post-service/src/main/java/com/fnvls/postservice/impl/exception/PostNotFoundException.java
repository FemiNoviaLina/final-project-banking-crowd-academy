package com.fnvls.postservice.impl.exception;

import lombok.Data;

@Data
public class PostNotFoundException extends RuntimeException{
    public String message = "PostNotFound";

    public PostNotFoundException() {}
    public PostNotFoundException(String message) {
        this.message = message;
    };
}