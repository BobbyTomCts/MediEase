package org.example.restapi.models;

import java.util.Objects;

public class NetworkHospital {

    private Long id;
    private String hospitalName;
    private String city;
    private String state;
    private String address;
    private String phone;
    private String specialties;
    private Double copayPercentage;

    public NetworkHospital() {}

    public NetworkHospital(String hospitalName, String city, String state, Double copayPercentage) {
        this.hospitalName = hospitalName;
        this.city = city;
        this.state = state;
        this.copayPercentage = copayPercentage;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSpecialties() { return specialties; }
    public void setSpecialties(String specialties) { this.specialties = specialties; }

    public Double getCopayPercentage() { return copayPercentage; }
    public void setCopayPercentage(Double copayPercentage) { this.copayPercentage = copayPercentage; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetworkHospital that = (NetworkHospital) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(hospitalName, that.hospitalName) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hospitalName, city, state);
    }
}
