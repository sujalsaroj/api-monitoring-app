    package com.sujal.API_monitoring.controller;

    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestMapping;

    import org.springframework.web.bind.annotation.RestController;

import com.sujal.API_monitoring.dto.ApiResponse;
import com.sujal.API_monitoring.dto.CreateApiRequest;
import com.sujal.API_monitoring.dto.UpdateApiRequest;
import com.sujal.API_monitoring.entity.MonitoredApi;
import com.sujal.API_monitoring.entity.MonitoringResult;
import com.sujal.API_monitoring.service.MonitoredApiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;
    
import com.sujal.API_monitoring.monitoring.ApiHealthChecker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

    @RestController
    @RequestMapping("/api/apis")
    @Tag(
    name = "Monitored APIs",
    description = "APIs for creating, viewing, updating, deleting and checking monitored APIs"
)
    public class MonitorApiController {
            
        private final MonitoredApiService monitoredApiService;
        private final ApiHealthChecker apiHealthChecker;

        public MonitorApiController(MonitoredApiService monitoredApiService, ApiHealthChecker apiHealthChecker) {
            this.monitoredApiService = monitoredApiService;
            this.apiHealthChecker = apiHealthChecker;

        }
        
        @Operation(
    summary = "Create monitored API",
    description = "Adds a new API to the authenticated user's monitoring list"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "201",
    description = "API created successfully"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "400",
    description = "Invalid request data"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "401",
    description = "Unauthorized"
)
        
        @PostMapping
        public ResponseEntity<ApiResponse> createApi( @Valid @RequestBody CreateApiRequest request,Authentication authenticaton
            
        ) {

            String email = authenticaton.getName();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(monitoredApiService.createApi(request, email));
        }
        
        @Operation(
    summary = "Get all monitored APIs",
    description = "Returns all APIs belonging to the authenticated user"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "200",
    description = "APIs retrieved successfully"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "401",
    description = "Unauthorized"
)


        @GetMapping
public List<ApiResponse> getAllApis(Authentication authentication) {

    String email = authentication.getName();
    return monitoredApiService.getAllApis(email);
}
        @Operation(
    summary = "Get monitored API by ID",
    description = "Returns a specific API belonging to the authenticated user"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "200",
    description = "API found successfully"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "404",
    description = "API not found"
)


        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse> getApiById(@PathVariable Long id, Authentication authentication) {
            String email = authentication.getName();
            ApiResponse response = monitoredApiService.getApiById(id, email);
            return ResponseEntity.ok(response);
        }
@Operation(
    summary = "Delete monitored API",
    description = "Deletes an API belonging to the authenticated user"
)   
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "204",
    description = "API deleted successfully"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "404",
    description = "API not found"
)
          @DeleteMapping("/{id}")
            public ResponseEntity<Void> deleteApi(
                    @PathVariable Long id,
                    Authentication authentication) {

            String email = authentication.getName();

             monitoredApiService.deleteById(id, email);

                return ResponseEntity.noContent().build();
    }

        @Operation(
    summary = "Update monitored API",
    description = "Updates an existing API belonging to the authenticated user"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "200",
    description = "API updated successfully"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "400",
    description = "Invalid request data"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "404",
    description = "API not found"
)

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse> updateApi(@PathVariable Long id, @Valid @RequestBody UpdateApiRequest request,
                Authentication authentication) {
            String email = authentication.getName();
            ApiResponse response = monitoredApiService.updateApi(id, request, email);
            return ResponseEntity.ok(response);
        }
        @Operation(
    summary = "Check API health",
    description = "Immediately performs a health check on the selected API"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "200",
    description = "Health check completed successfully"
)
@io.swagger.v3.oas.annotations.responses.ApiResponse(
    responseCode = "404",
    description = "API not found"
)
        @PostMapping("/{id}/check")
        public MonitoringResult checkApi(@PathVariable Long id
            ,Authentication authentication
         ) {
          String email= authentication.getName();
            MonitoredApi api = monitoredApiService.getApiEntityById(id,email);

             return apiHealthChecker.checkApi(api);
         }
    }
