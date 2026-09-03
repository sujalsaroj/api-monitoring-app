package com.sujal.API_monitoring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sujal.API_monitoring.dto.ApiResponse;
import com.sujal.API_monitoring.dto.CreateApiRequest;
import com.sujal.API_monitoring.dto.UpdateApiRequest;
import com.sujal.API_monitoring.entity.MonitoredApi;
import com.sujal.API_monitoring.entity.User;
import com.sujal.API_monitoring.exception.ApiNotFoundException;
import com.sujal.API_monitoring.exception.UserNotFoundException;
import com.sujal.API_monitoring.repository.MonitorApiRepository;
import com.sujal.API_monitoring.repository.UserRepository;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class MonitoredApiServiceTest {

    @Mock
    private MonitorApiRepository monitorApiRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MonitoredApiService monitoredApiService;

    @Test
    void showGetApiId() {
        //Arrange
        Long apiId = 1L;
        String email = "sujal@test.com";

        MonitoredApi api = new MonitoredApi();
        api.setId(apiId);
        api.setName("GITHUB API");

        when(monitorApiRepository.findByIdAndUserEmail(apiId, email)).thenReturn(Optional.of(api));

        //Act

        MonitoredApi result = monitoredApiService.getApiById(apiId, email);

        //Assert

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("GITHUB API", result.getName());

        verify(monitorApiRepository).findByIdAndUserEmail(apiId, email);

    }
    
    @Test
    void shouldThrowExceptionWhenApiNotFound() {
        Long apiId = 99L;
        String email = "Sujal@test.com";

        when(monitorApiRepository.findByIdAndUserEmail(apiId, email)).thenReturn(Optional.empty());

        ApiNotFoundException exception = assertThrows(ApiNotFoundException.class,
                () -> monitoredApiService.getApiById(apiId, email));

        assertEquals("API not found with id: " + apiId, exception.getMessage());

        verify(monitorApiRepository).findByIdAndUserEmail(apiId, email);
    }

    @Test
    void shouldDeleteApiWhenApiBelongsToUser() {
        Long apiId = 1L;
        String email = "Sujal@test.com";

        MonitoredApi api = new MonitoredApi();
        api.setId(apiId);
        api.setName("GITHUB API");

        when(monitorApiRepository.findByIdAndUserEmail(apiId, email)).thenReturn(Optional.of(api));

        monitoredApiService.deleteById(apiId, email);

        verify(monitorApiRepository).findByIdAndUserEmail(apiId, email);

        verify(monitorApiRepository).delete(api);

    }
    
    @Test
    void shouldThrowExceptionWhenDeletingApiNotFound() {
        Long apiId = 99L;
        String email = "Sujal@test.com";

        when(monitorApiRepository.findByIdAndUserEmail(apiId, email)).thenReturn(Optional.empty());

        assertThrows(ApiNotFoundException.class, () -> monitoredApiService.deleteById(apiId, email));

        verify(monitorApiRepository).findByIdAndUserEmail(apiId, email);

        verify(monitorApiRepository, never()).delete(any(MonitoredApi.class));

    }
    

    @Test
    void shouldCreateAPiSuccessfull() {
        String email = "Sujal@test.com";

        User user = new User();
        user.setId(1L);
        user.setName("sujal");
        user.setEmail(email);

        CreateApiRequest request = new CreateApiRequest();
        request.setName("GitHub API");
        request.setUrl("https://api.github.com");
        request.setCheckInterval(60);
        request.setActive(true);
        request.setHttpMethod("GET");
        request.setTimeout(5000);
        request.setExpectedStatusCode(200);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        when(monitorApiRepository.save(any(MonitoredApi.class))).thenAnswer(invocation -> {
            MonitoredApi api = invocation.getArgument(0);
            api.setId(1L);
            return api;
        });

        ApiResponse result = monitoredApiService.createApi(request, email);

        ArgumentCaptor<MonitoredApi> apiCaptor = ArgumentCaptor.forClass(MonitoredApi.class);
        verify(monitorApiRepository).save(apiCaptor.capture());
        MonitoredApi savedApi = apiCaptor.getValue();

        assertNotNull(result);
        assertEquals("GitHub API", savedApi.getName());
        assertEquals("https://api.github.com", savedApi.getUrl());
        assertEquals("GET", savedApi.getHttpMethod());
        assertEquals(200, savedApi.getExpectedStatusCode());
        assertEquals(60, savedApi.getCheckInterval());
        assertEquals(5000, savedApi.getTimeout());
        assertTrue(savedApi.getActive());

        assertEquals(user, savedApi.getUser());
        assertEquals(email, savedApi.getUser().getEmail());

        verify(userRepository).findByEmail(email);
        verify(monitorApiRepository).save(apiCaptor.capture());
    }
    

    @Test
    void shouldThrowExcpetionWhenCreatingApiAndUserNotFound() {

        String email = "Unkonwn@test.com";

        CreateApiRequest request = new CreateApiRequest();
        request.setName("GitHub API");
        request.setUrl("https://api.github.com");
        request.setHttpMethod("GET");
        request.setExpectedStatusCode(200);
        request.setCheckInterval(60);
        request.setTimeout(5000);
        request.setActive(true);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> monitoredApiService.createApi(request, email));

        verify(userRepository).findByEmail(email);

        verify(monitorApiRepository, never()).save(any(MonitoredApi.class));

    }
    
    @Test
    void shoudUpdateApiSuccessfully() {
        Long apiId = 1L;
        String email = "Sujal@test.com";

        MonitoredApi existingApi = new MonitoredApi();
        existingApi.setId(apiId);
        existingApi.setName("Old API");
        existingApi.setUrl("https://old-api.com");
        existingApi.setHttpMethod("GET");
        existingApi.setExpectedStatusCode(200);
        existingApi.setCheckInterval(30);
        existingApi.setTimeout(3000);
        existingApi.setActive(true);

        UpdateApiRequest request = new UpdateApiRequest();
        request.setName("Updated API");
        request.setUrl("https://updated-api.com");
        request.setHttpMethod("POST");
        request.setExpectedStatusCode(201);
        request.setCheckInterval(60);
        request.setTimeout(5000);
        request.setActive(false);

        when(monitorApiRepository.findByIdAndUserEmail(apiId, email)).thenReturn(Optional.of(existingApi));

        when(monitorApiRepository.save(any(MonitoredApi.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MonitoredApi result = monitoredApiService.updateApi(apiId, request, email);

        assertNotNull(result);
        assertEquals("Updated API", result.getName());
        assertEquals("https://updated-api.com", result.getUrl());
        assertEquals("POST", result.getHttpMethod());
        assertEquals(201, result.getExpectedStatusCode());
        assertEquals(60, result.getCheckInterval());
        assertEquals(5000, result.getTimeout());
        assertFalse(result.getActive());

        verify(monitorApiRepository).findByIdAndUserEmail(apiId, email);
        verify(monitorApiRepository).save(existingApi);

    }
    @Test
    void shouldThrowExceptionWhenUpdatingApiNotFound() {

        // Arrange
        Long apiId = 99L;
        String email = "sujal@test.com";

        UpdateApiRequest request = new UpdateApiRequest();
        request.setName("Updated API");
        request.setUrl("https://updated-api.com");
        request.setHttpMethod("GET");
        request.setExpectedStatusCode(200);
        request.setCheckInterval(60);
        request.setTimeout(5000);
        request.setActive(true);

        when(
                monitorApiRepository.findByIdAndUserEmail(apiId, email)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ApiNotFoundException.class,
                () -> monitoredApiService.updateApi(apiId, request, email));

        verify(monitorApiRepository)
                .findByIdAndUserEmail(apiId, email);

        verify(
                monitorApiRepository,
                never()).save(any(MonitoredApi.class));
    }

    @Test
    void shouldGetAllApisForUser() {
        String email = "sujal@test.com";

        MonitoredApi api1 = new MonitoredApi();
        api1.setId(1L);
        api1.setName("API 1");
        api1.setUrl("https://api1.com");

        MonitoredApi api2 = new MonitoredApi();
        api2.setId(2L);
        api2.setName("API 2");
        api2.setUrl("https://api2.com");

        List<MonitoredApi> apis = List.of(api1, api2);
        when(monitorApiRepository.findByUserEmail(email)).thenReturn(apis);

        List<MonitoredApi> result = monitoredApiService.getAllApis(email);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("API 1", result.get(0).getName());
        assertEquals("API 2", result.get(1).getName());

        verify(monitorApiRepository).findByUserEmail(email);
    }
    @Test
void shouldReturnEmptyListWhenUserHasNoApis() {

    // Arrange
    String email = "sujal@test.com";

    when(
        monitorApiRepository.findByUserEmail(email)
    ).thenReturn(List.of());

    // Act
    List<MonitoredApi> result =
            monitoredApiService.getAllApis(email);

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(monitorApiRepository)
            .findByUserEmail(email);
}
}
