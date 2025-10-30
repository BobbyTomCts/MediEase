package com.backend.mediassist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "requests")
public class Request {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    
    private Long empId;
    private Double requestAmount;
    private String status; // "PENDING", "APPROVED", "REJECTED"
    
    // Getters and Setters
    public Long getRequestId() {
        return requestId;
    }
    
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
    
    public Long getEmpId() {
        return empId;
    }
    
    public void setEmpId(Long empId) {
        this.empId = empId;
    }
    
    public Double getRequestAmount() {
        return requestAmount;
    }
    
    public void setRequestAmount(Double requestAmount) {
        this.requestAmount = requestAmount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
