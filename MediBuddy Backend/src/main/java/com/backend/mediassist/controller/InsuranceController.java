package com.backend.mediassist.controller;

import com.backend.mediassist.model.*;
import com.backend.mediassist.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {
    
    @Autowired
    private InsuranceService insuranceService;
    
    // Get all insurance packages
    @GetMapping("/packages")
    public List<InsuranceType> getAllPackages() {
        return insuranceService.getAllPackages();
    }
    
    // Create insurance for employee
    @PostMapping("/create")
    public Insurance createInsurance(@RequestParam Long empId, @RequestParam Long packageId) {
        return insuranceService.createInsurance(empId, packageId);
    }
    
    // Add dependant
    @PostMapping("/dependant/add")
    public Dependant addDependant(
            @RequestParam Long empId, 
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam String gender,
            @RequestParam String relation) {
        return insuranceService.addDependant(empId, name, age, gender, relation);
    }
    
    // Get dependants for employee
    @GetMapping("/dependants/{empId}")
    public List<Dependant> getDependants(@PathVariable Long empId) {
        return insuranceService.getDependants(empId);
    }
    
    // Update/Edit dependant
    @PutMapping("/dependant/update/{dependantId}")
    public Dependant updateDependant(
            @PathVariable Long dependantId,
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam String gender,
            @RequestParam String relation) {
        return insuranceService.updateDependant(dependantId, name, age, gender, relation);
    }
    
    // Delete/Remove dependant
    @DeleteMapping("/dependant/delete/{dependantId}")
    public String deleteDependant(@PathVariable Long dependantId) {
        return insuranceService.deleteDependant(dependantId);
    }
    
    // Get insurance for employee
    @GetMapping("/{empId}")
    public Insurance getInsurance(@PathVariable Long empId) {
        return insuranceService.getInsuranceByEmpId(empId);
    }
}
