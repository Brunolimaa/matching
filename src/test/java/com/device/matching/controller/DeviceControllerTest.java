package com.device.matching.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.device.matching.config.TestConfig;
import com.device.matching.dto.response.DeviceResponseDTO;
import com.device.matching.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
public class DeviceControllerTest {

    @MockBean
    private DeviceService deviceService;

    @Autowired
    private MockMvc mockMvc;

    private String userAgent;

    @BeforeEach
    void setUp() {
        userAgent = "Mozilla/5.0 (Linux NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    }

    @Test
    void testMatchDeviceCreated() throws Exception {

        DeviceResponseDTO mockResponse = new DeviceResponseDTO();
        mockResponse.setDeviceId("123");
        mockResponse.setOsName("Android");
        mockResponse.setCreated(true);
        mockResponse.setHitCount(1);

        when(deviceService.createOrUpdateDevice(anyString())).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/devices/match")
                        .contentType("application/json")
                        .content(userAgent))
                .andExpect(status().isCreated()) // Espera o status 201
                .andExpect(jsonPath("$.deviceId").value("123"))
                .andExpect(jsonPath("$.osName").value("Android"));
    }

    @Test
    void testMatchDeviceUpdated() throws Exception {

        DeviceResponseDTO mockResponse = new DeviceResponseDTO();
        mockResponse.setDeviceId("123");
        mockResponse.setOsName("Android");
        mockResponse.setHitCount(1);

        when(deviceService.createOrUpdateDevice(anyString())).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/devices/match")
                        .contentType("application/json")
                        .content(userAgent))
                .andExpect(status().isOk()) // Espera o status 201
                .andExpect(jsonPath("$.deviceId").value("123"))
                .andExpect(jsonPath("$.osName").value("Android"));
    }

    @Test
    void testGetDeviceByIdFound() throws Exception {
        DeviceResponseDTO mockResponse = new DeviceResponseDTO();
        mockResponse.setDeviceId("123");
        mockResponse.setOsName("Android");
        mockResponse.setHitCount(1);

        when(deviceService.getDeviceById(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/devices/{deviceId}", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId").value("123"))
                .andExpect(jsonPath("$.osName").value("Android"));
    }


    @Test
    void testGetDeviceByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/devices/{deviceId}", 123))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetDeviceByIdOsName() throws Exception {
        DeviceResponseDTO mockResponse = new DeviceResponseDTO();
        mockResponse.setDeviceId("123");
        mockResponse.setOsName("Android");
        mockResponse.setHitCount(1);

        when(deviceService.getDevicesByOs(anyString())).thenReturn(Arrays.asList(mockResponse));

        mockMvc.perform(get("/api/v1/devices/os/{osName}", "Android"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].deviceId").value("123"))
                .andExpect(jsonPath("$[0].osName").value("Android"));
    }

    @Test
    void testGetDeviceByIdOsNameNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/devices/os/{osName}", "Android"))
                .andExpect(status().isNotFound());
    }
}
