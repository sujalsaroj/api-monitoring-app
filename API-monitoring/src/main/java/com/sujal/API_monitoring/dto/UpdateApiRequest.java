package com.sujal.API_monitoring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateApiRequest {
 @NotBlank(message = "API name is required")
    private String name;

    @NotBlank(message = "URL is required")
    private String url;

    @NotBlank(message = "HTTP method is required")
    private String httpMethod;

    @NotNull(message = "Expected status code is required")
    private Integer expectedStatusCode;

    @NotNull(message = "Check interval is required")
    @Min(value = 1, message = "Check interval must be at least 1 second")
    private Integer checkInterval;

    @NotNull(message = "Timeout is required")
    @Min(value = 100, message = "Timeout must be at least 100 milliseconds")
    private Integer timeout;

    @NotNull(message = "Active status is required")
    private Boolean active;
}