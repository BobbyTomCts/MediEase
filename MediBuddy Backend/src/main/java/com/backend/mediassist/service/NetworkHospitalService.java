package com.backend.mediassist.service;

import com.backend.mediassist.model.NetworkHospital;
import com.backend.mediassist.repository.NetworkHospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NetworkHospitalService {
    
    @Autowired
    private NetworkHospitalRepository networkHospitalRepository;
    
    // Get all network hospitals
    public List<NetworkHospital> getAllHospitals() {
        return networkHospitalRepository.findAll();
    }
    
    // Get hospitals by city
    public List<NetworkHospital> getHospitalsByCity(String city) {
        return networkHospitalRepository.findByCity(city);
    }
    
    // Get hospitals by state
    public List<NetworkHospital> getHospitalsByState(String state) {
        return networkHospitalRepository.findByState(state);
    }
    
    // Get hospital by ID
    public NetworkHospital getHospitalById(Long id) {
        return networkHospitalRepository.findById(id).orElse(null);
    }
}
