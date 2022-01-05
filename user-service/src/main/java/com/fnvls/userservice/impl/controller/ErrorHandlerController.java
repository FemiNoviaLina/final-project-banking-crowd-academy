package com.fnvls.userservice.impl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fnvls.userservice.api.response.BaseResponse;
import com.fnvls.userservice.impl.exception.EmailAlreadyRegisteredException;
import com.fnvls.userservice.impl.exception.MyFileNotFoundException;
import com.fnvls.userservice.impl.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<?> handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String defaultMessage = fieldError.getDefaultMessage();
        BaseResponse<?> response = new BaseResponse<>(null);
        response.setMessage(defaultMessage);
        response.setSuccess(false);
        return response;
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<?> handleMissingPathVariableException(MissingPathVariableException ex) {
        String message = ex.getMessage();
        BaseResponse<?> response = new BaseResponse<>(null);
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<?> handleBadCredentialsException(MissingPathVariableException ex) {
        String message = ex.getMessage();
        BaseResponse<?> response = new BaseResponse<>(null);
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<?> handleUserNotFoundException(Exception ex) {
        String message = ex.getMessage();
        BaseResponse<?> response = new BaseResponse<>(null);
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

    @ExceptionHandler(MyFileNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<?> handleMyFileNotFoundException(Exception ex) {
        String message = ex.getMessage();
        BaseResponse<?> response = new BaseResponse<>(null);
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<?> EmailAlreadyRegisteredException(Exception ex) {
        String message = ex.getMessage();
        BaseResponse<?> response = new BaseResponse<>(null);
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ResponseBody
    public BaseResponse<?> LockedException(Exception ex) {
        String message = "Account has not been enabled";
        BaseResponse<?> response = new BaseResponse<>(null);
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<?> handleGeneralException(Exception ex) {
        String message = ex.getMessage();
        BaseResponse<?> response = new BaseResponse<>(null);
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse<?> handleJsonProcessingException(MissingPathVariableException ex) {
        String message = "Error while parsing JSON";
        BaseResponse<?> response = new BaseResponse<>(null);
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse<?> handleIOException(MissingPathVariableException ex) {
        String message = "Error while getting file";
        BaseResponse<?> response = new BaseResponse<>(null);
        response.setMessage(message);
        response.setSuccess(false);
        return response;
    }
}