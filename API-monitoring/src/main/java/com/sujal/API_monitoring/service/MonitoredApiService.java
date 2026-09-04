package com.sujal.API_monitoring.service;




import java.util.List;

import org.springframework.stereotype.Service;

import com.sujal.API_monitoring.dto.ApiResponse;
import com.sujal.API_monitoring.dto.CreateApiRequest;
import com.sujal.API_monitoring.dto.UpdateApiRequest;
import com.sujal.API_monitoring.entity.MonitoredApi;
import com.sujal.API_monitoring.entity.User;
import com.sujal.API_monitoring.exception.ApiNotFoundException;
import com.sujal.API_monitoring.exception.UserNotFoundException;
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

    public ApiResponse createApi(CreateApiRequest request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
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

    public List<ApiResponse> getAllApis(String email) {
        List<MonitoredApi> apis = monitorApiRepository.findByUserEmail(email);
        return apis.stream().map(this::convertToResponse).toList();
     }

     public ApiResponse getApiById(Long id,String email) {
         MonitoredApi api = monitorApiRepository.findByIdAndUserEmail(id, email)
                 .orElseThrow(() -> new ApiNotFoundException("API not found with id: " + id));
         return convertToResponse(api);
     }
    

     public void deleteById(Long id, String email) {
         MonitoredApi api = monitorApiRepository.findByIdAndUserEmail(id, email)
                 .orElseThrow(() -> new ApiNotFoundException("Api not found with id: " + id));
         monitorApiRepository.delete(api);
     }

     public ApiResponse updateApi(Long id, UpdateApiRequest request, String email) {
         MonitoredApi existingApi = monitorApiRepository.findByIdAndUserEmail(id, email)
                 .orElseThrow(() -> new ApiNotFoundException("API not Found"));

         existingApi.setName(request.getName());
         existingApi.setCheckInterval(request.getCheckInterval());
         existingApi.setActive(request.getActive());
         existingApi.setHttpMethod(request.getHttpMethod());
         existingApi.setTimeout(request.getTimeout());
         existingApi.setExpectedStatusCode(request.getExpectedStatusCode());
         existingApi.setUrl(request.getUrl());
         MonitoredApi updateApi = monitorApiRepository.save(existingApi);
         return convertToResponse(updateApi);
     }
     
    // GET ENTITY FOR INTERNAL BACKEND USE
    // Example: ApiHealthChecker
    public MonitoredApi getApiEntityById(
            Long id,
            String email) {

        return monitorApiRepository
                .findByIdAndUserEmail(id, email)
                .orElseThrow(() ->
                        new ApiNotFoundException(
                                "API not found with id: " + id));
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
