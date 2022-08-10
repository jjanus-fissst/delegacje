package com.lbd.projectlbd.exception;

import org.springframework.http.HttpStatus;

public class DelegationValidationException extends BusinessException{

    public DelegationValidationException() { super(HttpStatus.BAD_REQUEST, "Delegation data is not valid!"); }

    public DelegationValidationException(String message) { super(HttpStatus.BAD_REQUEST, message); }
}

