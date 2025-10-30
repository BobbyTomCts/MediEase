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
        Dependant dependant = dependantRepository.findById(dependantId).orElse(null);
        
        if (dependant == null) {
            throw new RuntimeException("Dependant not found with ID: " + dependantId);
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
