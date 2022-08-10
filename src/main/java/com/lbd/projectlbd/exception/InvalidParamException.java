package com.lbd.projectlbd.exception;

import org.springframework.http.HttpStatus;

public class InvalidParamException extends BusinessException{

    public InvalidParamException() { super(HttpStatus.BAD_REQUEST, "Invalid param was passed!"); }

    public InvalidParamException(String message) { super(HttpStatus.BAD_REQUEST, message); }
}

