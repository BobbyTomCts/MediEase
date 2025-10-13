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
    public Dependant addDependant(@RequestParam Long empId, @RequestParam String name) {
        return insuranceService.addDependant(empId, name);
    }

    // Get dependants for employee
    @GetMapping("/dependants/{empId}")
    public List<Dependant> getDependants(@PathVariable Long empId) {
        return insuranceService.getDependants(empId);
    }

    // Get insurance for employee
    @GetMapping("/{empId}")
    public Insurance getInsurance(@PathVariable Long empId) {
        return insuranceService.getInsuranceByEmpId(empId);
    }
}
