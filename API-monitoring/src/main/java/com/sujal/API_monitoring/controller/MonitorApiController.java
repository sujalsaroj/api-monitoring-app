    package com.sujal.API_monitoring.controller;

    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
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
        public ApiResponse creatApi( @Valid @RequestBody CreateApiRequest request,@RequestParam Long userId) {
            return monitoredApiService.createApi(request,userId);
        }

        @GetMapping
        public List<MonitoredApi> getAllApis() {
            return monitoredApiService.getAllApis();
        }

        @GetMapping("/{id}")
        public MonitoredApi getApiById(@PathVariable Long id) {
            return monitoredApiService.getApiById(id);
        }

        @DeleteMapping("/{id}")
        public void deleteApi(@PathVariable Long id) {
            monitoredApiService.deleteById(id);
        }

        @PutMapping("/{id}")
        public MonitoredApi updateApi( @PathVariable Long id, @Valid @RequestBody UpdateApiRequest request) {
            return monitoredApiService.updateApi(id, request);
        }
        @PostMapping("/{id}/check")
         public MonitoringResult checkApi(@PathVariable Long id) {

            MonitoredApi api = monitoredApiService.getApiById(id);

             return apiHealthChecker.checkApi(api);
         }
    }
