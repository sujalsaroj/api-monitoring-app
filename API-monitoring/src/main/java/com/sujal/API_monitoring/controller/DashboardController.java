package com.sujal.API_monitoring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sujal.API_monitoring.dto.ApiDashboardResponse;
import com.sujal.API_monitoring.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping
    public List<ApiDashboardResponse> getDashboard() {

        return dashboardService.getDashboard();
    }
}