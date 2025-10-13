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
    public Dependant addDependant(Long empId, String dependantName) {
        Dependant dependant = new Dependant();
        dependant.setName(dependantName);
        dependant.setDependantFor(empId);

        return dependantRepository.save(dependant);
    }

    // Get dependants for employee
    public List<Dependant> getDependants(Long empId) {
        return dependantRepository.findByDependantFor(empId);
    }

    // Get insurance for employee
    public Insurance getInsuranceByEmpId(Long empId) {
        return insuranceRepository.findByEmpId(empId);
    }
}

