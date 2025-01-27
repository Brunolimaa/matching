package com.device.matching.service;

import com.device.matching.dto.response.DeviceResponseDTO;

import java.util.List;

public interface DeviceService {

    DeviceResponseDTO createOrUpdateDevice(String userAgent);

    List<DeviceResponseDTO> getDevicesByOs(String osName);

    DeviceResponseDTO getDeviceById(String deviceId);

    void deleteDevice(String deviceId);
}
