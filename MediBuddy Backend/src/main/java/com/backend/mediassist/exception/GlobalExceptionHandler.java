package com.backend.mediassist.exception;

import com.backend.mediassist.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST API
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();
        
        // Determine status based on message
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        if (message != null) {
            if (message.contains("already exists")) {
                status = HttpStatus.CONFLICT;
            } else if (message.contains("not found")) {
                status = HttpStatus.NOT_FOUND;
            } else if (message.contains("Invalid") || message.contains("required")) {
                status = HttpStatus.BAD_REQUEST;
            }
        }
        
        ErrorResponse errorResponse = new ErrorResponse(message, status.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            "An unexpected error occurred",
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
