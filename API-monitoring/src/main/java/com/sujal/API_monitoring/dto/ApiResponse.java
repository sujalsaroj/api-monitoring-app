package com.sujal.API_monitoring.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {

    private Long id;
    private String name;
    private String url;
    private String httpMethod;
    private Integer expectedStatusCode;
    private Integer checkInterval;
    private Integer timeout;
    private Boolean active;
    private LocalDateTime createdAt;

    private Long userId;
}