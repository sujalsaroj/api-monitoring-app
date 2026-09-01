package com.sujal.API_monitoring.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitoringResultResponse {

    private Long id;

    private String status;

    private Integer statusCode;

    private Long responseTime;

    private LocalDateTime createAt;

    private String errorMessage;
}