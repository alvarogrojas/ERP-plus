package com.ndl.erp.exceptions;


import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Getter
//@Setter
//@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> errors;

    public ErrorResponse(
            int status, String message
    ) {
        this.status = status;
        this.message = message;
    }

//    @Getter
//    @Setter
//    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
        ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}

    public void addValidationError(String field, String message){
        if(Objects.isNull(errors)){
            errors = new ArrayList<>();
        }
        errors.add(new ValidationError(field, message));
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }
}
