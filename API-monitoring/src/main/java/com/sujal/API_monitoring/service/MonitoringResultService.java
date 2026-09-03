package com.sujal.API_monitoring.service;

import com.sujal.API_monitoring.dto.ApiStatsResponse;
import com.sujal.API_monitoring.dto.MonitoringResultResponse;
import com.sujal.API_monitoring.entity.MonitoredApi;
import com.sujal.API_monitoring.entity.MonitoringResult;
import com.sujal.API_monitoring.exception.ApiNotFoundException;
import com.sujal.API_monitoring.repository.MonitorApiRepository;
import com.sujal.API_monitoring.repository.MonitorResultRepository;
import java.util.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

@Service
public class MonitoringResultService {
    private final MonitorResultRepository monitorResultRepository;
    private final MonitorApiRepository monitorApiRepository;

    public MonitoringResultService(MonitorApiRepository monitorApiRepository,
            MonitorResultRepository monitorResultRepository) {
        this.monitorApiRepository = monitorApiRepository;
        this.monitorResultRepository = monitorResultRepository;
    }

    public Page<MonitoringResultResponse> getResultsByApiId(Long apiId,int page, int size,String email) {

    monitorApiRepository.findByIdAndUserEmail(apiId, email).orElseThrow(()->
         new ApiNotFoundException("Api Not Found with Id: " + apiId));

        
        Pageable pageable = PageRequest.of(page, size);
        Page<MonitoringResult> results = monitorResultRepository
                .findByApiIdOrderByCreateAtDesc(apiId,pageable);

        return results.map(this::mapToResponse);
            
    }
      private MonitoringResultResponse mapToResponse(
              MonitoringResult result) {

          MonitoringResultResponse response = new MonitoringResultResponse();

          response.setId(result.getId());
          response.setStatus(result.getStatus());
          response.setStatusCode(result.getStatusCode());
          response.setResponseTime(result.getResponseTime());
          response.setCreateAt(result.getCreateAt());
          response.setErrorMessage(result.getErrorMessage());

          return response;
      }
    
      public ApiStatsResponse getApiStats(Long apiId,String email) {
          MonitoredApi api = monitorApiRepository.findByIdAndUserEmail(apiId,email)
                  .orElseThrow(() -> new ApiNotFoundException("Api Not Found with Id: " + apiId));

          List<MonitoringResult> results = monitorResultRepository.findByApiIdOrderByCreateAtDesc(apiId);

          ApiStatsResponse response = new ApiStatsResponse();
          response.setApiId(api.getId());
          response.setApiName(api.getName());

          if (results.isEmpty()) {
              response.setLatestStatus("NOT_CHECKED");
              response.setAverageResponseTime(0);
              response.setUpTimePercentage(0);
              response.setTotalChecks(0);

              return response;

          }

          MonitoringResult latestResults = results.get(0);

          response.setLatestStatus(latestResults.getStatus());

          long totalChecks = results.size();

          long upChecks = results.stream().filter(result -> "UP".equals(result.getStatus())).count();

          double uptimePercentage = ((double) upChecks / totalChecks) * 100;
          
          double averageResponseTime = results.stream().mapToLong(MonitoringResult::getResponseTime).average()
                  .orElse(0);

          response.setTotalChecks(totalChecks);
          response.setUpTimePercentage(Math.round(uptimePercentage));
          response.setAverageResponseTime(Math.round(averageResponseTime));
        
          return response;
      }
}
