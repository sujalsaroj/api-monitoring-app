package com.sujal.API_monitoring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sujal.API_monitoring.dto.ApiDashboardResponse;
import com.sujal.API_monitoring.entity.MonitoredApi;
import com.sujal.API_monitoring.entity.MonitoringResult;
import com.sujal.API_monitoring.repository.MonitorApiRepository;
import com.sujal.API_monitoring.repository.MonitorResultRepository;

@Service
public class DashboardService {

    private final MonitorApiRepository monitorApiRepository;
    private final MonitorResultRepository monitorResultRepository;

    public DashboardService(
            MonitorApiRepository monitorApiRepository,
            MonitorResultRepository monitorResultRepository) {

        this.monitorApiRepository = monitorApiRepository;
        this.monitorResultRepository = monitorResultRepository;
    }

    public List<ApiDashboardResponse> getDashboard() {

        List<MonitoredApi> apis =
                monitorApiRepository.findAll();

        return apis.stream()
                .map(this::mapToDashboard)
                .toList();
    }

    private ApiDashboardResponse mapToDashboard(
            MonitoredApi api) {

        ApiDashboardResponse response =
                new ApiDashboardResponse();

        response.setApiId(api.getId());
        response.setApiName(api.getName());
        response.setUrl(api.getUrl());
        response.setActive(api.getActive());

        MonitoringResult latestResult =
                monitorResultRepository
                        .findTopByApiIdOrderByCreateAtDesc(
                                api.getId()
                        )
                        .orElse(null);

        if (latestResult == null) {

            response.setStatus("NOT_CHECKED");
            response.setStatusCode(null);
            response.setResponseTime(null);
            response.setLastCheckedAt(null);

        } else {

            response.setStatus(
                    latestResult.getStatus()
            );

            response.setStatusCode(
                    latestResult.getStatusCode()
            );

            response.setResponseTime(
                    latestResult.getResponseTime()
            );

            response.setLastCheckedAt(
                    latestResult.getCreateAt()
            );
        }

        return response;
    }
}