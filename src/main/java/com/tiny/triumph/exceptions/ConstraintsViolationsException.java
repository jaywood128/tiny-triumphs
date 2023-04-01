package com.tiny.triumph.exceptions;

import com.tiny.triumph.model.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import java.util.Set;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConstraintsViolationsException extends RuntimeException {

    public  Set<ConstraintViolation<Todo>> violations;

    public  ConstraintsViolationsException(Set<ConstraintViolation<Todo>> violations){
        this.violations = violations;
    }

    public Set<ConstraintViolation<Todo>> getViolations() {
        return violations;
    }
}
