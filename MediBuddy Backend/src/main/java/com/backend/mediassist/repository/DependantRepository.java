package com.backend.mediassist.repository;

import com.backend.mediassist.model.Dependant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DependantRepository extends JpaRepository<Dependant, Long> {
    List<Dependant> findByDependantFor(Long empId);
}
