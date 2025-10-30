package com.backend.mediassist.repository;

import com.backend.mediassist.model.NetworkHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NetworkHospitalRepository extends JpaRepository<NetworkHospital, Long> {
    List<NetworkHospital> findByCity(String city);
    List<NetworkHospital> findByState(String state);
}
