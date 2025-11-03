package org.example.restapi.models;

public class InsuranceType {
    private Long insuranceId;
    private String packageName;
    private Double amount;

    // Default constructor for Rest Assured deserialization
    public InsuranceType() {
    }

    // Getters and Setters
    public Long getInsuranceId() { return insuranceId; }
    public void setInsuranceId(Long insuranceId) { this.insuranceId = insuranceId; }
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}
