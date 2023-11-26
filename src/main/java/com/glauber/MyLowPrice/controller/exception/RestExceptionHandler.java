package com.glauber.MyLowPrice.controller.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Foi encontrado um erro de parâmetro",
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse constraintViolationException(ConstraintViolationException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Foi encontrato um erro de validação",
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse productAlreadyExistsException(ProductAlreadyExistsException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Product Already exist",
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(PriceAlertAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse priceAlertAlreadyExistException(PriceAlertAlreadyExistException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Price Alert Already exist",
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse nullPointerException(NullPointerException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Erro interno do servidor, objeto nulo encontrado",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

}
