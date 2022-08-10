package com.lbd.projectlbd.exception;

import org.springframework.http.HttpStatus;

public class InvalidParamException extends BusinessException{

    InvalidParamException() { super(HttpStatus.BAD_REQUEST, "Invalid param was passed!"); }

    public InvalidParamException(String message) { super(HttpStatus.BAD_REQUEST, message); }
}

