package com.glauber.MyLowPrice.controller.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
    private String status;
    private Integer statusCode;
    private String error;
}
