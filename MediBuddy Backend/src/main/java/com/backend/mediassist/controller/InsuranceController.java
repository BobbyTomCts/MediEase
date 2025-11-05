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
    
    @GetMapping("/packages")
    public List<InsuranceType> getAllPackages() {
        return insuranceService.getAllPackages();
    }
    
    @PostMapping("/create")
    public Insurance createInsurance(
            @RequestParam Long empId,
            @RequestParam Long packageId) {
        return insuranceService.createInsurance(empId, packageId);
    }
    
    @PostMapping("/dependant/add")
    public Dependant addDependant(
            @RequestParam Long empId, 
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam String gender,
            @RequestParam String relation) {
        return insuranceService.addDependant(empId, name, age, gender, relation);
    }
    
    @GetMapping("/dependants/{empId}")
    public List<Dependant> getDependants(@PathVariable Long empId) {
        return insuranceService.getDependants(empId);
    }
    
    @PutMapping("/dependant/update/{dependantId}")
    public Dependant updateDependant(
            @PathVariable Long dependantId,
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam String gender,
            @RequestParam String relation) {
        return insuranceService.updateDependant(dependantId, name, age, gender, relation);
    }
    
    @DeleteMapping("/dependant/delete/{dependantId}")
    public MessageResponse deleteDependant(@PathVariable Long dependantId) {
        return insuranceService.deleteDependant(dependantId);
    }
    
    @GetMapping("/{empId}")
    public Insurance getInsurance(@PathVariable Long empId) {
        return insuranceService.getInsuranceByEmpId(empId);
    }
}
