package com.tiny.triumph.response;

import com.tiny.triumph.model.Todo;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ConstraintsViolationsResponse {

    public Set<ConstraintViolation<Todo>> violationsExceptions;

    public ConstraintsViolationsResponse(Set<ConstraintViolation<Todo>> violationsExceptions) {
        this.violationsExceptions = violationsExceptions;
    }

    public Set<ConstraintViolation<Todo>> getViolationsExceptions() {
        return violationsExceptions;
    }
}
