package com.tiny.triumph.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Throwable {
    private static final long serialVersionUID = 1L;
    private String message;
    public UserNotFoundException(String message) {
        this.message = message;
    }
}
