package com.tiny.triumph.exceptions;

import java.util.List;

public class FieldErrorResponse {
    public List<CustomFieldError> fieldErrors;

    public FieldErrorResponse() {
    }

    public List<CustomFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<CustomFieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
