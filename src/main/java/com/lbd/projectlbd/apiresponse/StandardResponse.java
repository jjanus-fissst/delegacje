package com.lbd.projectlbd.apiresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter @Getter
public class StandardResponse {

//    private HttpStatus status;
    private ApiVersion apiVersion;
    private Integer status;
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiError> apiErrorList;

    public StandardResponse() {
        this.apiVersion = ApiVersion.v1;
        this.timestamp = LocalDateTime.now();
    }

    public StandardResponse(HttpStatus status, String message) {
        this();
        this.status = status.value();
        this.message = message;
    }

    public StandardResponse(ApiVersion apiVersion, HttpStatus status, String message) {
        this();
        this.apiVersion = apiVersion;
        this.status = status.value();
        this.message = message;
    }

    public StandardResponse(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status.value();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public StandardResponse(ApiVersion apiVersion, HttpStatus status, String message, Throwable ex) {
        this();
        this.apiVersion = apiVersion;
        this.status = status.value();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    private void addError(ApiError error) {
        if (apiErrorList == null)
            apiErrorList = new ArrayList<>();
        apiErrorList.add(error);
    }

    /**
     * Validation errors (for @Valid ResponseBody)
     * */
    public void addValidationError(String objectName, String message) {
        addError(new ApiValidationError(objectName, message));
    }

    public void addValidationError(String objectName, String fieldName, Object rejectedValue, String message) {
        addError(new ApiValidationError(objectName, fieldName, rejectedValue, message));
    }

    public void addValidationError(ObjectError objectError) {
        if (objectError != null)
            addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    public void addValidationError(FieldError fieldError) {
        if (fieldError != null)
            addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrorList) {
        fieldErrorList.forEach(this::addValidationError);
    }


    /**
     * Build ResponseEntity message
     * */
    public ResponseEntity<StandardResponse> buildResponseEntity() {
        return ResponseEntity.status(this.getStatus()).body(this);
    }

    public ResponseEntity<Object> buildResponseEntityObject() {
        return ResponseEntity.status(this.getStatus()).body(this);
    }

    public enum ApiVersion {
        v1, v2
    }

}
