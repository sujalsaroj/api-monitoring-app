package com.sujal.API_monitoring.entity;

import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "monitoringResult")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private Integer statusCode;

    private Long responseTime;

    private LocalDateTime createAt;
       @PrePersist
protected void onCreate() {
    createAt = LocalDateTime.now();
}


    @Column(length = 1000)
    private String errorMessage;
    
    @ManyToOne
    @JoinColumn(name="api_id",nullable = false)
    private MonitoredApi api;

}
