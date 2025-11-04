package com.backend.mediassist.controller;

import com.backend.mediassist.model.NetworkHospital;
import com.backend.mediassist.service.NetworkHospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@Tag(name = "Network Hospitals", description = "APIs for managing network hospitals")
public class NetworkHospitalController {
    
    @Autowired
    private NetworkHospitalService networkHospitalService;
    
    @Operation(summary = "Get all hospitals", description = "Retrieve all network hospitals")
    @GetMapping("/all")
    public List<NetworkHospital> getAllHospitals() {
        return networkHospitalService.getAllHospitals();
    }
    
    @Operation(summary = "Get hospitals by city", description = "Retrieve hospitals in a specific city")
    @GetMapping("/city/{city}")
    public List<NetworkHospital> getHospitalsByCity(@Parameter(description = "City name") @PathVariable String city) {
        return networkHospitalService.getHospitalsByCity(city);
    }
    
    @Operation(summary = "Get hospitals by state", description = "Retrieve hospitals in a specific state")
    @GetMapping("/state/{state}")
    public List<NetworkHospital> getHospitalsByState(@Parameter(description = "State name") @PathVariable String state) {
        return networkHospitalService.getHospitalsByState(state);
    }
    
    @Operation(summary = "Get hospital by ID", description = "Retrieve hospital details by ID")
    @GetMapping("/{id}")
    public ResponseEntity<NetworkHospital> getHospitalById(@Parameter(description = "Hospital ID") @PathVariable Long id) {
        NetworkHospital hospital = networkHospitalService.getHospitalById(id);

        if (hospital == null) {
            //return 404 NOT FOUND status
            return ResponseEntity.notFound().build();
        }

        // If found, return 200 OK with the hospital data
        return ResponseEntity.ok(hospital);
    }
}
