package com.glauber.MyLowPrice.controller.exception;

public class PriceAlertAlreadyExistException extends RuntimeException {
    public PriceAlertAlreadyExistException(String message) {
        super(message);
    }
}
