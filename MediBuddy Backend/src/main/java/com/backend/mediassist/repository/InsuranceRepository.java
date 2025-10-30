package com.backend.mediassist.repository;

import com.backend.mediassist.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    Insurance findByEmpId(Long empId);
}
