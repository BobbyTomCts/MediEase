package com.backend.mediassist.controller;

import com.backend.mediassist.model.*;
import com.backend.mediassist.service.InsuranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/insurance")
@Tag(name = "Insurance & Dependants", description = "APIs for managing insurance packages and dependants")
public class InsuranceController {
    
    @Autowired
    private InsuranceService insuranceService;
    
    @Operation(summary = "Get all insurance packages", description = "Retrieve all available insurance packages")
    @GetMapping("/packages")
    public List<InsuranceType> getAllPackages() {
        return insuranceService.getAllPackages();
    }
    
    @Operation(summary = "Create insurance for employee", description = "Create a new insurance policy for an employee")
    @PostMapping("/create")
    public Insurance createInsurance(
            @Parameter(description = "Employee ID") @RequestParam Long empId,
            @Parameter(description = "Package ID") @RequestParam Long packageId) {
        return insuranceService.createInsurance(empId, packageId);
    }
    
    @Operation(summary = "Add dependant", description = "Add a dependant to employee's insurance")
    @PostMapping("/dependant/add")
    public Dependant addDependant(
            @Parameter(description = "Employee ID") @RequestParam Long empId, 
            @Parameter(description = "Dependant name") @RequestParam String name,
            @Parameter(description = "Dependant age") @RequestParam Integer age,
            @Parameter(description = "Dependant gender") @RequestParam String gender,
            @Parameter(description = "Relation (Spouse/Father/Mother/Child/Sibling)") @RequestParam String relation) {
        return insuranceService.addDependant(empId, name, age, gender, relation);
    }
    
    @Operation(summary = "Get dependants", description = "Get all dependants for an employee")
    @GetMapping("/dependants/{empId}")
    public List<Dependant> getDependants(@Parameter(description = "Employee ID") @PathVariable Long empId) {
        return insuranceService.getDependants(empId);
    }
    
    @Operation(summary = "Update dependant", description = "Update dependant information")
    @PutMapping("/dependant/update/{dependantId}")
    public Dependant updateDependant(
            @Parameter(description = "Dependant ID") @PathVariable Long dependantId,
            @Parameter(description = "Dependant name") @RequestParam String name,
            @Parameter(description = "Dependant age") @RequestParam Integer age,
            @Parameter(description = "Dependant gender") @RequestParam String gender,
            @Parameter(description = "Relation") @RequestParam String relation) {
        return insuranceService.updateDependant(dependantId, name, age, gender, relation);
    }
    
    @Operation(summary = "Delete dependant", description = "Remove a dependant from employee's insurance")
    @DeleteMapping("/dependant/delete/{dependantId}")
    public MessageResponse deleteDependant(@Parameter(description = "Dependant ID") @PathVariable Long dependantId) {
        return insuranceService.deleteDependant(dependantId);
    }
    
    @Operation(summary = "Get employee insurance", description = "Get insurance details for an employee")
    @GetMapping("/{empId}")
    public Insurance getInsurance(@Parameter(description = "Employee ID") @PathVariable Long empId) {
        return insuranceService.getInsuranceByEmpId(empId);
    }
}
