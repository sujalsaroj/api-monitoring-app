package com.sujal.API_monitoring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiStatsResponse {
    private Long apiId;
    private String apiName;
    private String latestStatus;
    private double upTimePercentage;
    private double averageResponseTime;
    private long totalChecks;
}
