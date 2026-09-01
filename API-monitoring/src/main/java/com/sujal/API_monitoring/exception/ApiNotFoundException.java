package com.sujal.API_monitoring.exception;

public class ApiNotFoundException extends RuntimeException {
    
    public ApiNotFoundException(String message) {
        super(message);
    }
}
