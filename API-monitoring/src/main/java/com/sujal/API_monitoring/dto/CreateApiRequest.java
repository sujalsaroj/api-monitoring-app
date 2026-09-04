package com.sujal.API_monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for creating a monitored API")
public class CreateApiRequest {
    
       @Schema(
        description = "Display name of the API",
        example = "GitHub API"
    )

    @NotBlank(message = "API name is required")
    private String name;

    @Schema(
        description = "URL that will be monitored",
        example = "https://api.github.com"
    )
    @NotBlank(message = "URL is required")
    private String url;


     @Schema(
        description = "HTTP method used for the health check",
        example = "GET"
    )
    @NotBlank(message = "HTTP method is required")
    private String httpMethod;



    @Schema(
        description = "Expected HTTP status code",
        example = "200"
    )
    @NotNull(message = "Expected status code is required")
    private Integer expectedStatusCode;


    @Schema(
        description = "Interval between health checks in seconds",
        example = "60"
    )
    @NotNull(message = "Check interval is required")
    @Min(value = 1, message = "Check interval must be at least 1 second")
    private Integer checkInterval;

     @Schema(
        description = "Maximum request timeout in milliseconds",
        example = "5000"
    )
    @NotNull(message = "Timeout is required")
    @Min(value = 100, message = "Timeout must be at least 100 milliseconds")
    private Integer timeout;

       @Schema(
        description = "Whether automatic monitoring is enabled",
        example = "true"
    )
    @NotNull(message = "Active status is required")
    private Boolean active;
}