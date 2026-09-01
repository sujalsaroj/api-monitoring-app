package com.sujal.API_monitoring.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ApiNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleApiNotFound(ApiNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

     @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        
         Map<String, String> errors = new HashMap<>();
        
         ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.
getField(), error.getDefaultMessage()));        
         return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
     
        
    }
}
