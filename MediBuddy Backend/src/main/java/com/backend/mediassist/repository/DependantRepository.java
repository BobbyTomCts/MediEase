package com.backend.mediassist.repository;

import com.backend.mediassist.model.Dependant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DependantRepository extends JpaRepository<Dependant, Long> {
    List<Dependant> findByDependantFor(Long empId);
    
    // Check for duplicate dependant
    Optional<Dependant> findByNameIgnoreCaseAndAgeAndRelationAndDependantFor(
        String name, Integer age, String relation, Long empId);
    
    // Count dependants for an employee
    long countByDependantFor(Long empId);
}
