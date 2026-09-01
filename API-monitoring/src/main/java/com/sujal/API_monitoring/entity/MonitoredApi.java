package com.sujal.API_monitoring.entity;

import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
@Entity
@Table(name="monitored_apis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonitoredApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    private String httpMethod;

    private Integer expectedStatusCode;

    private Integer checkInterval;
    
    private Integer timeout;

    private Boolean active;

    private LocalDateTime createAt;
       @PrePersist
protected void onCreate() {
    createAt = LocalDateTime.now();
}
    
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
     
    @OneToMany(mappedBy = "api")
    private List<MonitoringResult> monitoringResult;

    
}
