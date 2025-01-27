package com.device.matching.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.device.matching.dto.response.DeviceResponseDTO;
import com.device.matching.exception.ResourceNotFoundException;
import com.device.matching.model.Device;
import com.device.matching.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import java.util.List;

class DeviceServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;

    private Device device;
    private DeviceResponseDTO deviceResponseDTO;
    private String userAgent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mocking a Device object for tests
        device = new Device();
        device.setDeviceId("linux-91.0.4472.124");
        device.setOsName("Linux");
        device.setBrowserName("Chrome");
        device.setBrowserVersion("91.0.4472.124");
        device.setHitCount(0);

        // Mocking DeviceResponseDTO
        deviceResponseDTO = new DeviceResponseDTO();
        deviceResponseDTO.setOsName("Linux");
        deviceResponseDTO.setOsVersion("91.0.4472.124");
        deviceResponseDTO.setHitCount(0);

        userAgent = "Mozilla/5.0 (Linux NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    }

    @Test
    void testCreateOrUpdateDevice_whenDeviceExists() {
        when(deviceRepository.findById(device.getDeviceId())).thenReturn(Optional.of(device));
        when(deviceRepository.updateHit(any(Device.class))).thenReturn(device);

        DeviceResponseDTO result = deviceServiceImpl.createOrUpdateDevice(userAgent);

        assertNotNull(result);
        assertEquals(device.getDeviceId(), result.getDeviceId());
        verify(deviceRepository, times(1)).updateHit(any(Device.class));
    }

    @Test
    void testCreateOrUpdateDevice_whenDeviceDoesNotExist() {
        when(deviceRepository.findById(device.getDeviceId())).thenReturn(Optional.empty());
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        DeviceResponseDTO result = deviceServiceImpl.createOrUpdateDevice(userAgent);

        assertNotNull(result);
        assertEquals(device.getDeviceId(), result.getDeviceId());
        assertEquals(1, result.getHitCount());
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void testGetDevicesByOs_whenDevicesExist() {
        List<Device> devices = List.of(device);
        when(deviceRepository.findByOsName("Chrome")).thenReturn(devices);

        List<DeviceResponseDTO> result = deviceServiceImpl.getDevicesByOs("Chrome");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(device.getDeviceId(), result.get(0).getDeviceId());
    }

    @Test
    void testGetDevicesByOs_whenDevicesNotFound() {
        when(deviceRepository.findByOsName("iOS")).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            deviceServiceImpl.getDevicesByOs("iOS");
        });

        assertEquals("No devices found for OS: iOS", exception.getMessage());
    }

    @Test
    void testGetDeviceById_whenDeviceExists() {
        when(deviceRepository.findById(device.getDeviceId())).thenReturn(Optional.of(device));

        DeviceResponseDTO result = deviceServiceImpl.getDeviceById(device.getDeviceId());

        assertNotNull(result);
        assertEquals(device.getDeviceId(), result.getDeviceId());
    }

    @Test
    void testGetDeviceById_whenDeviceNotFound() {
        when(deviceRepository.findById(device.getDeviceId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            deviceServiceImpl.getDeviceById(device.getDeviceId());
        });

        assertEquals("Device not found with ID: "+device.getDeviceId(), exception.getMessage());
    }

    @Test
    void testDeleteDevice_whenDeviceExists() {
        when(deviceRepository.findById(device.getDeviceId())).thenReturn(Optional.of(device));

        deviceServiceImpl.deleteDevice(device.getDeviceId());

        verify(deviceRepository, times(1)).deleteById(device.getDeviceId());
    }

    @Test
    void testDeleteDevice_whenDeviceNotFound() {
        when(deviceRepository.findById(device.getDeviceId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            deviceServiceImpl.deleteDevice(device.getDeviceId());
        });

        assertEquals("Device not found with ID: "+device.getDeviceId(), exception.getMessage());
    }
}
