package com.lbd.projectlbd.apiresponse;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ApiValidationError implements ApiError {

    private String objectName;
    private String fieldName;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String objectName, String fieldName, Object rejectedValue, String message) {
        this.objectName = objectName;
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    ApiValidationError(String objectName, String message) {
        this.objectName = objectName;
        this.message = message;
    }



}
