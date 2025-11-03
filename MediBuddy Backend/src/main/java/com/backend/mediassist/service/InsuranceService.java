package com.backend.mediassist.service;

import com.backend.mediassist.model.*;
import com.backend.mediassist.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InsuranceService {
    
    @Autowired
    private InsuranceTypeRepository insuranceTypeRepository;
    
    @Autowired
    private InsuranceRepository insuranceRepository;
    
    @Autowired
    private DependantRepository dependantRepository;
    
    // Get all insurance packages
    public List<InsuranceType> getAllPackages() {
        return insuranceTypeRepository.findAll();
    }
    
    // Create insurance for employee
    public Insurance createInsurance(Long empId, Long packageId) {
        // 1. Check for duplicate insurance (MUST be added)
        if (insuranceRepository.findByEmpId(empId) != null) {
            throw new RuntimeException("Insurance already exists for employee " + empId);
        }
        InsuranceType insuranceType = insuranceTypeRepository.findById(packageId).orElse(null);
        
        if (insuranceType == null) {
            return null;
        }
        
        Insurance insurance = new Insurance();
        insurance.setEmpId(empId);
        insurance.setPackageName(insuranceType.getPackageName());
        insurance.setAmountRemaining(insuranceType.getAmount());
        
        return insuranceRepository.save(insurance);
    }
    
    // Add dependant
    public Dependant addDependant(Long empId, String dependantName, Integer age, String gender, String relation) {
        // Validate relation
        if (!relation.equals("Spouse") && !relation.equals("Child") && 
            !relation.equals("Father") && !relation.equals("Mother") && !relation.equals("Sibling")) {
            throw new RuntimeException("Invalid relation. Allowed values: Spouse, Child, Father, Mother, Sibling");
        }
        
        // Check if maximum number of dependants (4) reached
        long currentCount = dependantRepository.countByDependantFor(empId);
        if (currentCount >= 4) {
            throw new RuntimeException("Maximum of 4 dependants allowed per employee");
        }
        
        // Check for Father/Mother uniqueness - only one of each allowed
        if (relation.equals("Father") || relation.equals("Mother")) {
            List<Dependant> existingParents = dependantRepository.findByDependantForAndRelation(empId, relation);
            if (!existingParents.isEmpty()) {
                throw new RuntimeException("You can only add one " + relation.toLowerCase() + " as a dependant");
            }
        }
        
        // Check for duplicate dependant (same name, age, and relation)
        dependantRepository.findByNameIgnoreCaseAndAgeAndRelationAndDependantFor(
            dependantName, age, relation, empId
        ).ifPresent(existing -> {
            throw new RuntimeException("A dependant with the same name, age, and relation already exists");
        });
        
        Dependant dependant = new Dependant();
        dependant.setName(dependantName);
        dependant.setAge(age);
        dependant.setGender(gender);
        dependant.setRelation(relation);
        dependant.setDependantFor(empId);
        
        return dependantRepository.save(dependant);
    }
    
    // Get dependants for employee
    public List<Dependant> getDependants(Long empId) {
        return dependantRepository.findByDependantFor(empId);
    }
    
    // Update/Edit dependant
    public Dependant updateDependant(Long dependantId, String name, Integer age, String gender, String relation) {
        // Validate relation
        if (!relation.equals("Spouse") && !relation.equals("Child") && 
            !relation.equals("Father") && !relation.equals("Mother") && !relation.equals("Sibling")) {
            throw new RuntimeException("Invalid relation. Allowed values: Spouse, Child, Father, Mother, Sibling");
        }
        
        Dependant dependant = dependantRepository.findById(dependantId).orElse(null);
        
        if (dependant == null) {
            throw new RuntimeException("Dependant not found with ID: " + dependantId);
        }
        
        // If changing to Father or Mother, check if one already exists (excluding current dependant)
        if ((relation.equals("Father") || relation.equals("Mother")) && 
            !dependant.getRelation().equals(relation)) {
            List<Dependant> existingParents = dependantRepository.findByDependantForAndRelation(
                dependant.getDependantFor(), relation);
            if (!existingParents.isEmpty()) {
                throw new RuntimeException("You can only have one " + relation.toLowerCase() + " as a dependant");
            }
        }
        
        dependant.setName(name);
        dependant.setAge(age);
        dependant.setGender(gender);
        dependant.setRelation(relation);
        
        return dependantRepository.save(dependant);
    }
    
    // Delete/Remove dependant
    public String deleteDependant(Long dependantId) {
        Dependant dependant = dependantRepository.findById(dependantId).orElse(null);
        
        if (dependant == null) {
            throw new RuntimeException("Dependant not found with ID: " + dependantId);
        }
        
        dependantRepository.deleteById(dependantId);
        return "Dependant deleted successfully";
    }
    
    // Get insurance for employee
    public Insurance getInsuranceByEmpId(Long empId) {
        return insuranceRepository.findByEmpId(empId);
    }
}
