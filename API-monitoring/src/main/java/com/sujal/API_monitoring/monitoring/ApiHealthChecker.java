package com.sujal.API_monitoring.monitoring;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.sujal.API_monitoring.entity.MonitoredApi;
import com.sujal.API_monitoring.entity.MonitoringResult;
import com.sujal.API_monitoring.repository.MonitorResultRepository;

@Service
public class ApiHealthChecker {

    private final MonitorResultRepository monitorResultRepository;

    public ApiHealthChecker(
            MonitorResultRepository monitorResultRepository) {

        this.monitorResultRepository = monitorResultRepository;
    }

    public MonitoringResult checkApi(MonitoredApi api) {

        RestClient client =
                createRestClient(api.getTimeout());

        long startTime =
                System.currentTimeMillis();

        MonitoringResult result =
                new MonitoringResult();

        try {

            Integer statusCode = client
                    .method(
                        HttpMethod.valueOf(
                            api.getHttpMethod().toUpperCase()
                        )
                    )
                    .uri(api.getUrl())
                    .exchange((request, response) ->
                        response.getStatusCode().value()
                    );

            long responseTime =
                    System.currentTimeMillis() - startTime;

            result.setStatusCode(statusCode);
            result.setResponseTime(responseTime);
            result.setCreateAt(LocalDateTime.now());
            result.setApi(api);

            if (statusCode.equals(
                    api.getExpectedStatusCode())) {

                result.setStatus("UP");

            } else {

                result.setStatus("DOWN");
            }

            result.setErrorMessage(null);

        } catch (Exception e) {

            long responseTime =
                    System.currentTimeMillis() - startTime;

            result.setStatus("DOWN");

            result.setStatusCode(null);

            result.setResponseTime(responseTime);

            result.setCreateAt(LocalDateTime.now());

            result.setErrorMessage(e.getMessage());

            result.setApi(api);
        }

        return monitorResultRepository.save(result);
    }

    private RestClient createRestClient(
            Integer timeout) {

        JdkClientHttpRequestFactory requestFactory =
                new JdkClientHttpRequestFactory();

        requestFactory.setReadTimeout(
                Duration.ofMillis(timeout)
        );

        return RestClient.builder()
                .requestFactory(requestFactory)
                .build();
    }
}