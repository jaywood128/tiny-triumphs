package com.tiny.triumph.controller;

import com.tiny.triumph.exceptions.ResourceNotFoundException;
import com.tiny.triumph.exceptions.TodoConstraintsViolationsException;
import com.tiny.triumph.exceptions.UserConstraintsViolationsException;
import com.tiny.triumph.exceptions.UserNotFoundException;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.response.ConstraintsViolationsResponse;
import com.tiny.triumph.response.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TodoConstraintsViolationsException.class})
    public ResponseEntity<ConstraintsViolationsResponse> handleConstraintViolationException(TodoConstraintsViolationsException ex) {
        Set<ConstraintViolation<Todo>> constraintViolations = ex.getViolations();
        ConstraintsViolationsResponse response = new ConstraintsViolationsResponse(constraintViolations);

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(UserConstraintsViolationsException.class)
    public ResponseEntity<Object> handleUserConstraintsViolationsException(UserConstraintsViolationsException ex) {
        Set<ConstraintViolation<com.tiny.triumph.model.User>> violations = ex.getViolations();
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<com.tiny.triumph.model.User> violation : violations) {
            errorMessages.add(violation.getPropertyPath() + " " + violation.getMessage());
        }
        // Create a custom error response object
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessages.toString());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Customize the error response based on your requirements
        String errorMessage = "Duplicate entry error. Email already exists.";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), errorMessage);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

}
