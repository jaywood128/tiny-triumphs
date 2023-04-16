package com.tiny.triumph.exceptions;

import com.tiny.triumph.dto.RegistrationRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import java.util.Set;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserConstraintsViolationsException extends RuntimeException {

    public Set<ConstraintViolation<RegistrationRequestDTO>> violations;

    public UserConstraintsViolationsException(Set<ConstraintViolation<RegistrationRequestDTO>> violations){
        this.violations = violations;
    }

    public Set<ConstraintViolation<RegistrationRequestDTO>> getViolations() {
        return violations;
    }

}
