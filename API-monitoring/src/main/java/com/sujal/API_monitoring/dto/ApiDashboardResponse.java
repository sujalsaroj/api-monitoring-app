package com.sujal.API_monitoring.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiDashboardResponse {

    private Long apiId;
    private String apiName;
    private String url;
    private Boolean active;

    private String status;
    private Integer statusCode;
    private Long responseTime;
    private LocalDateTime lastCheckedAt;
}