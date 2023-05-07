package com.tiny.triumph.exceptions;


import com.tiny.triumph.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import java.util.Set;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserConstraintsViolationsException extends Throwable {

    public Set<ConstraintViolation<User>> violations;

    public UserConstraintsViolationsException(Set<ConstraintViolation<User>> violations){
        this.violations = violations;
    }

    public Set<ConstraintViolation<User>> getViolations() {
        return violations;
    }

}
