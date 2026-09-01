package com.sujal.API_monitoring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sujal.API_monitoring.entity.MonitoringResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MonitorResultRepository extends JpaRepository<MonitoringResult, Long> {
    
    Optional<MonitoringResult> findTopByApiIdOrderByCreateAtDesc(Long id);

    List<MonitoringResult> findByApiIdOrderByCreateAtDesc(Long id);

    Page<MonitoringResult> findByApiIdOrderByCreateAtDesc(Long id, Pageable pageable);
    
}
