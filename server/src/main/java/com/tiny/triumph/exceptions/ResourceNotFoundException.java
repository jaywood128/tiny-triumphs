package com.tiny.triumph.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Throwable {
    @Serial
    public static final long serialVersionUID = 1L;
    public String message;
    public ResourceNotFoundException(String message) {
        this.message = message;
    }
}