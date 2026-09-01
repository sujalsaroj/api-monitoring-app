package com.sujal.API_monitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sujal.API_monitoring.entity.MonitoredApi;

public interface MonitorApiRepository extends JpaRepository<MonitoredApi, Long> {

    
} 
