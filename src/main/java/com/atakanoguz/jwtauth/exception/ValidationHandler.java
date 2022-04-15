package com.atakanoguz.jwtauth.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus httpStatus, WebRequest webRequest) {
        HashMap<Object,Object> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors()
                .forEach(element -> {
                    String fieldName = ((FieldError) element).getField();
                    String messagee = element.getDefaultMessage();
                    errors.put(fieldName,messagee);
                });

        ValidationException validationException = new ValidationException("Validation Error", errors);

        return new ResponseEntity<>(validationException, validationException.getHttpStatus());

    }

}
