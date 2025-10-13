package com.backend.mediassist.repository;

import com.backend.mediassist.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByEmpId(Long empId);
    List<Request> findByStatus(String status);
}
