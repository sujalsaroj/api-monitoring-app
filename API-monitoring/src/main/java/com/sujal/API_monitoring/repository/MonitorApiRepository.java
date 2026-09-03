package com.sujal.API_monitoring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sujal.API_monitoring.entity.MonitoredApi;

public interface MonitorApiRepository extends JpaRepository<MonitoredApi, Long> {
    List<MonitoredApi> findByUserEmail(String email);

    Optional<MonitoredApi> findByIdAndUserEmail(Long id, String email); 
    
} 
