package com.backend.mediassist.model;

/**
 * Error response model for API errors
 */
public class ErrorResponse {
    private String message;
    private String status;
    private long timestamp;

    public ErrorResponse(String message, String status) {
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
