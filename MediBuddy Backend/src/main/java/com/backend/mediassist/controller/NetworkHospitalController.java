package com.backend.mediassist.controller;

import com.backend.mediassist.model.NetworkHospital;
import com.backend.mediassist.service.NetworkHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class NetworkHospitalController {
    
    @Autowired
    private NetworkHospitalService networkHospitalService;
    
    // Get all network hospitals
    @GetMapping("/all")
    public List<NetworkHospital> getAllHospitals() {
        return networkHospitalService.getAllHospitals();
    }
    
    // Get hospitals by city
    @GetMapping("/city/{city}")
    public List<NetworkHospital> getHospitalsByCity(@PathVariable String city) {
        return networkHospitalService.getHospitalsByCity(city);
    }
    
    // Get hospitals by state
    @GetMapping("/state/{state}")
    public List<NetworkHospital> getHospitalsByState(@PathVariable String state) {
        return networkHospitalService.getHospitalsByState(state);
    }
    
    // Get hospital by ID
//    @GetMapping("/{id}")
//    public NetworkHospital getHospitalById(@PathVariable Long id) {
//        return networkHospitalService.getHospitalById(id);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<NetworkHospital> getHospitalById(@PathVariable Long id) {
        NetworkHospital hospital = networkHospitalService.getHospitalById(id);

        if (hospital == null) {
            //return 404 NOT FOUND status
            return ResponseEntity.notFound().build();
        }

        // If found, return 200 OK with the hospital data
        return ResponseEntity.ok(hospital);
    }
}
