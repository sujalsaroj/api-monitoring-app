package com.sujal.API_monitoring.service;




import java.util.List;

import org.springframework.stereotype.Service;

import com.sujal.API_monitoring.dto.ApiResponse;
import com.sujal.API_monitoring.dto.CreateApiRequest;
import com.sujal.API_monitoring.dto.UpdateApiRequest;
import com.sujal.API_monitoring.entity.MonitoredApi;
import com.sujal.API_monitoring.entity.User;
import com.sujal.API_monitoring.exception.ApiNotFoundException;
import com.sujal.API_monitoring.repository.MonitorApiRepository;
import com.sujal.API_monitoring.repository.UserRepository;

@Service
public class MonitoredApiService {

    private final MonitorApiRepository monitorApiRepository;
    private final UserRepository userRepository;

    public MonitoredApiService(MonitorApiRepository monitorApiRepository,
            UserRepository  userRepository
    ) {
        this.monitorApiRepository = monitorApiRepository;
        this.userRepository = userRepository;
    }

    public ApiResponse createApi(CreateApiRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        MonitoredApi api = new MonitoredApi();
        api.setName(request.getName());
        api.setUrl(request.getUrl());
        api.setCheckInterval(request.getCheckInterval());
        api.setActive(request.getActive());
        api.setExpectedStatusCode(request.getExpectedStatusCode());
        api.setHttpMethod(request.getHttpMethod());
        api.setTimeout(request.getTimeout());
        api.setUser(user);

        MonitoredApi savedApi = monitorApiRepository.save(api);
        return convertToResponse(savedApi);
    }

    public List<MonitoredApi> getAllApis() {
        return monitorApiRepository.findAll();
     }

     public MonitoredApi getApiById(Long id) {
         return monitorApiRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("API not found with id: "+id));
     }
    
     public void deleteById(Long id) {
         monitorApiRepository.deleteById(id);
     }

     public MonitoredApi updateApi(Long id, UpdateApiRequest request) {
         MonitoredApi existingApi = monitorApiRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("API not Found"));

         existingApi.setName(request.getName());
         existingApi.setCheckInterval(request.getCheckInterval());
         existingApi.setActive(request.getActive());
         existingApi.setHttpMethod(request.getHttpMethod());
         existingApi.setTimeout(request.getTimeout());
         existingApi.setExpectedStatusCode(request.getExpectedStatusCode());
         existingApi.setUrl(request.getUrl());

         return monitorApiRepository.save(existingApi);
     }

     private ApiResponse convertToResponse(MonitoredApi api) {
         ApiResponse response = new ApiResponse();
         response.setId(api.getId());
         response.setName(api.getName());
         response.setUrl(api.getUrl());
         response.setHttpMethod(api.getHttpMethod());
         response.setExpectedStatusCode(api.getExpectedStatusCode());
         response.setCheckInterval(api.getCheckInterval());
         response.setTimeout(api.getTimeout());
         response.setActive(api.getActive());
         response.setCreatedAt(api.getCreateAt());

         if (api.getUser() != null) {
             response.setUserId(api.getUser().getId());
         }

         return response;

     }
     
    

}
