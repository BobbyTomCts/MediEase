package org.example.restapi.models;

public class Insurance {
    private Long insuranceId;
    private Long empId;
    private String packageName;
    private Double amountRemaining;

    // Default constructor for Rest Assured deserialization
    public Insurance() {
    }

    // Getters and Setters
    public Long getInsuranceId() { return insuranceId; }
    public void setInsuranceId(Long insuranceId) { this.insuranceId = insuranceId; }
    public Long getEmpId() { return empId; }
    public void setEmpId(Long empId) { this.empId = empId; }
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public Double getAmountRemaining() { return amountRemaining; }
    public void setAmountRemaining(Double amountRemaining) { this.amountRemaining = amountRemaining; }
}