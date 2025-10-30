package com.backend.mediassist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "insurance_types")
public class InsuranceType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insuranceId;
    
    private String packageName;
    private Double amount;
    
    // Getters and Setters
    public Long getInsuranceId() {
        return insuranceId;
    }
    
    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
