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

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;
    
import com.sujal.API_monitoring.monitoring.ApiHealthChecker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

    @RestController
    @RequestMapping("/api/apis")
    public class MonitorApiController {
            
        private final MonitoredApiService monitoredApiService;
        private final ApiHealthChecker apiHealthChecker;

        public MonitorApiController(MonitoredApiService monitoredApiService , ApiHealthChecker apiHealthChecker) {
            this.monitoredApiService = monitoredApiService;
            this.apiHealthChecker = apiHealthChecker;

        }
        
        @PostMapping
        public ResponseEntity<ApiResponse> createApi( @Valid @RequestBody CreateApiRequest request,Authentication authenticaton
            
        ) {

            String email = authenticaton.getName();
            return ResponseEntity.status(HttpStatus.CREATED)
            .body(monitoredApiService.createApi(request, email));
        }

        @GetMapping
        public List<MonitoredApi> getAllApis(Authentication authentication) {

            String email = authentication.getName();
            return monitoredApiService.getAllApis(email)    ;
        }

        @GetMapping("/{id}")
        public MonitoredApi getApiById(@PathVariable Long id, Authentication authentication) {
            String email = authentication.getName();
            return monitoredApiService.getApiById(id,email);
        }

        @DeleteMapping("/{id}")
        public void deleteApi(@PathVariable Long id, Authentication authentication) {
            String email = authentication.getName();
            monitoredApiService.deleteById(id,email);
        }

        @PutMapping("/{id}")
        public MonitoredApi updateApi(@PathVariable Long id, @Valid @RequestBody UpdateApiRequest request,
                Authentication authentication) {
            String email= authentication.getName();
            return monitoredApiService.updateApi(id, request,email);
        }
        @PostMapping("/{id}/check")
        public MonitoringResult checkApi(@PathVariable Long id
            ,Authentication authentication
         ) {
          String email= authentication.getName();
            MonitoredApi api = monitoredApiService.getApiById(id,email);

             return apiHealthChecker.checkApi(api);
         }
    }
