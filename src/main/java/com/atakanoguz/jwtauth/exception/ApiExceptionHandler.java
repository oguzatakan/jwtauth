package com.atakanoguz.jwtauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException exception) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(exception.getMessage(),badRequest);

        return new ResponseEntity<>(apiException, badRequest);

    }

}
