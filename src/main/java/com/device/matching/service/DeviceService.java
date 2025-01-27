package com.device.matching.service;

import com.device.matching.dto.response.DeviceResponseDTO;
import com.device.matching.exception.ResourceNotFoundException;
import com.device.matching.mapper.DeviceMapper;
import com.device.matching.model.Device;
import com.device.matching.repository.DeviceRepository;
import com.device.matching.util.ErrorMessages;
import com.device.matching.util.UserAgentParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DeviceService {


    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    public DeviceResponseDTO createOrUpdateDevice(String userAgent) {

        Device device = UserAgentParser.parseUserAgent(userAgent);

        Optional<Device> existingDevice = repository.findById(device.getDeviceId());

        if (existingDevice.isPresent()) {
            Device updatedDevice = repository.updateHit(existingDevice.get());
            return DeviceMapper.INSTANCE.deviceToDeviceResponseDTO(updatedDevice);
        } else {
            device.setHitCount(1);
            device.setCreated(true);
            repository.save(device);
            return DeviceMapper.INSTANCE.deviceToDeviceResponseDTO(device);
        }

    }

    public List<DeviceResponseDTO> getDevicesByOs(String osName) {
        List<Device> devices = repository.findByOsName(osName);

        if (devices.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.DEVICE_NOT_FOUND_BY_OS, osName));
        }

        return devices.stream()
                .map(DeviceMapper.INSTANCE::deviceToDeviceResponseDTO)
                .collect(Collectors.toList());
    }

    public DeviceResponseDTO getDeviceById(String deviceId) {
        Device device = repository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.DEVICE_NOT_FOUND, deviceId)));

        return DeviceMapper.INSTANCE.deviceToDeviceResponseDTO(device);
    }

    public void deleteDevice(String deviceId) {
        Device device = repository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.DEVICE_NOT_FOUND, deviceId)));

        repository.deleteById(device.getDeviceId());
    }

}