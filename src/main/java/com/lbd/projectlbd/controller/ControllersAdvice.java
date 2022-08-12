package com.lbd.projectlbd.controller;

import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.exception.DelegationValidationException;
import com.lbd.projectlbd.exception.InvalidParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllersAdvice extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ControllersAdvice.class);

    @ExceptionHandler(MissingRequiredPropertiesException.class)
    public ResponseEntity<StandardResponse> handleMissingRequiredProperties(MissingRequiredPropertiesException ex) {
        StandardResponse errorResponse = new StandardResponse(HttpStatus.BAD_REQUEST, "Missing param(s)", ex);
        return errorResponse.buildResponseEntity();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardResponse> handleEntityNotfound(EntityNotFoundException ex) {
        StandardResponse errorResponse = new StandardResponse(HttpStatus.BAD_REQUEST, "Not found", ex);
        return errorResponse.buildResponseEntity();
    }

    @ExceptionHandler(DelegationValidationException.class)
    public ResponseEntity<StandardResponse> handleDelegationValidation(DelegationValidationException ex) {
        StandardResponse errorResponse = new StandardResponse(ex.getStatus(), "Delegation exception", ex);
        return errorResponse.buildResponseEntity();
    }

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<StandardResponse> handleDelegationValidation(InvalidParamException ex) {
        StandardResponse errorResponse = new StandardResponse(ex.getStatus(), "Invalid param exception", ex);
        return errorResponse.buildResponseEntity();
    }

    /** For @RequestParam */
    @Override protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StandardResponse errorResponse = new StandardResponse(HttpStatus.BAD_REQUEST, "Missing param "+ex.getParameterName(), ex);
        return errorResponse.buildResponseEntityObject();
    }

    /** For @Valid (Controller layer) */
    @Override protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StandardResponse errorResponse = new StandardResponse(HttpStatus.BAD_REQUEST, "Validation error", ex);
        errorResponse.addValidationError(ex.getBindingResult().getGlobalError());
        errorResponse.addValidationErrors(ex.getBindingResult().getFieldErrors());
        return errorResponse.buildResponseEntityObject();
    }
    /** For @Valid + @Validated (Service layer) */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardResponse> handleConstraindViolationException(ConstraintViolationException ex) {
        StandardResponse errorResponse = new StandardResponse(StandardResponse.ApiVersion.v2, HttpStatus.BAD_REQUEST, "Validation error");
        for (ConstraintViolation cv : ex.getConstraintViolations()) {
            errorResponse.addValidationError(cv.getPropertyPath().toString(), cv.getMessage());
        }

        return errorResponse.buildResponseEntity();
    }

}
