package com.backend.mediassist.repository;

import com.backend.mediassist.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByEmpId(Long empId);
    List<Request> findByStatus(String status);
    
    // Filter by status and date range
    @Query("SELECT r FROM Request r WHERE r.status = :status AND r.createdAt >= :startDate AND r.createdAt <= :endDate ORDER BY r.createdAt DESC")
    List<Request> findByStatusAndDateRange(@Param("status") String status, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // Filter by date range
    @Query("SELECT r FROM Request r WHERE r.createdAt >= :startDate AND r.createdAt <= :endDate ORDER BY r.createdAt DESC")
    List<Request> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // Find all sorted by creation date
    @Query("SELECT r FROM Request r ORDER BY r.createdAt DESC")
    List<Request> findAllOrderByCreatedAtDesc();
}
