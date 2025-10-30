package com.backend.mediassist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "network_hospitals")
public class NetworkHospital {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String hospitalName;
    private String city;
    private String state;
    private String address;
    private String phone;
    private String specialties; // Comma-separated specialties
    
    @Column(nullable = false)
    private Double copayPercentage; // Percentage of cost patient pays (e.g., 10.0 means 10% copay)
    
    // Constructors
    public NetworkHospital() {}
    
    public NetworkHospital(String hospitalName, String city, String state, String address, String phone, String specialties, Double copayPercentage) {
        this.hospitalName = hospitalName;
        this.city = city;
        this.state = state;
        this.address = address;
        this.phone = phone;
        this.specialties = specialties;
        this.copayPercentage = copayPercentage;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getHospitalName() {
        return hospitalName;
    }
    
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getSpecialties() {
        return specialties;
    }
    
    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }
    
    public Double getCopayPercentage() {
        return copayPercentage;
    }
    
    public void setCopayPercentage(Double copayPercentage) {
        this.copayPercentage = copayPercentage;
    }
}
