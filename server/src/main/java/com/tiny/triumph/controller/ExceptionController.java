package com.tiny.triumph.controller;

import com.tiny.triumph.exceptions.ConstraintsViolationsException;
import com.tiny.triumph.exceptions.ResourceNotFoundException;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.response.ConstraintsViolationsResponse;
import com.tiny.triumph.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import java.util.Set;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = { ConstraintsViolationsException.class })
    public ResponseEntity<ConstraintsViolationsResponse> handleConstraintViolationException(ConstraintsViolationsException ex) {
        Set<ConstraintViolation<Todo>> constraintViolations = ex.getViolations();
        ConstraintsViolationsResponse response = new ConstraintsViolationsResponse(constraintViolations);

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
