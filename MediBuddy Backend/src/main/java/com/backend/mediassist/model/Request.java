package com.backend.mediassist.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class Request {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    
    private Long empId;
    private Double requestAmount;
    private Double approvedAmount; // Amount after copay deduction
    private String status; // "PENDING", "APPROVED", "REJECTED"
    private Long hospitalId; // Hospital selected by user
    private Double copayAmount; // Copay amount deducted
    
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime approvedAt;
    
    // Constructors
    public Request() {
        this.createdAt = LocalDateTime.now();
    }
    
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
    
    public Double getApprovedAmount() {
        return approvedAmount;
    }
    
    public void setApprovedAmount(Double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getHospitalId() {
        return hospitalId;
    }
    
    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }
    
    public Double getCopayAmount() {
        return copayAmount;
    }
    
    public void setCopayAmount(Double copayAmount) {
        this.copayAmount = copayAmount;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }
    
    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }
}
