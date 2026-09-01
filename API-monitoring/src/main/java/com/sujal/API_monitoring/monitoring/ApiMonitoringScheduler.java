package com.sujal.API_monitoring.monitoring;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sujal.API_monitoring.entity.MonitoredApi;
import com.sujal.API_monitoring.entity.MonitoringResult;
import com.sujal.API_monitoring.repository.MonitorApiRepository;
import com.sujal.API_monitoring.repository.MonitorResultRepository;

@Component
public class ApiMonitoringScheduler {

    private final ApiHealthChecker apiHealthChecker;
    private final MonitorApiRepository monitorApiRepository;
    private final MonitorResultRepository monitorResultRepository;
    public ApiMonitoringScheduler(ApiHealthChecker apiHealthChecker, MonitorApiRepository monitorApiRepository, MonitorResultRepository monitorResultRepository) {
        this.apiHealthChecker = apiHealthChecker;
        this.monitorApiRepository = monitorApiRepository;
        this.monitorResultRepository = monitorResultRepository;

    }
    
    @Scheduled(fixedRate = 10000)
    public void monitorApis() {
        List<MonitoredApi> apis = monitorApiRepository.findAll();
        for (MonitoredApi api : apis) {
            if (!Boolean.TRUE.equals(api.getActive())) {
                continue;
            }
            Optional<MonitoringResult> lastestResult = monitorResultRepository
                    .findTopByApiIdOrderByCreateAtDesc(api.getId());
              
            if (lastestResult.isEmpty()) {
                apiHealthChecker.checkApi(api);
                continue;
            }
        
            MonitoringResult lastCheck = lastestResult.get();

            LocalDateTime nextCheckTime = lastCheck.getCreateAt().plusSeconds(api.getCheckInterval());

            if (LocalDateTime.now().isAfter(nextCheckTime) || LocalDateTime.now().isEqual(nextCheckTime)) {
                apiHealthChecker.checkApi(api); 
            }
        }
    }
    
}
