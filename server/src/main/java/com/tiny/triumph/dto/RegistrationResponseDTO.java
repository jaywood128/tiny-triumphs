package com.tiny.triumph.dto;

public class RegistrationResponseDTO {
    private String message;

    public RegistrationResponseDTO(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
