package com.glauber.MyLowPrice.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse noSUchElementException(NoSuchElementException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Not found",
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }
}
