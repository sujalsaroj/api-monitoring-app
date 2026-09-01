package com.sujal.API_monitoring.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sujal.API_monitoring.dto.ApiStatsResponse;
import com.sujal.API_monitoring.dto.MonitoringResultResponse;


import com.sujal.API_monitoring.service.MonitoringResultService;


@RestController
@RequestMapping("api/apis")
public class MonitoringResultController {

    private final MonitoringResultService monitoringResultService;
    MonitoringResultController(MonitoringResultService monitoringResultService) {
        this.monitoringResultService = monitoringResultService;

    }

    @GetMapping("/{apiId}/results")
    public Page<MonitoringResultResponse> getResults(@PathVariable Long apiId,@RequestParam(defaultValue = "0" )int page,@RequestParam(defaultValue = "10") int size) {
        return monitoringResultService.getResultsByApiId(apiId ,page,size);

    } 
    
    @GetMapping("/{apiId}/stats")
    public ApiStatsResponse getApiStats(@PathVariable Long apiId) {
        return monitoringResultService.getApiStats(apiId);
    }
    
}
