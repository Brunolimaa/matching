package com.device.matching.controller;

import com.device.matching.dto.response.DeviceResponseDTO;
import com.device.matching.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
public class DeviceController implements DeviceApi {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public ResponseEntity<DeviceResponseDTO> matchDevice(@RequestBody String userAgent) {
        try {
            var responseDTO = deviceService.createOrUpdateDevice(userAgent);
            if(responseDTO.isCreated()) {
                return ResponseEntity.created(URI.create("/api/v1/devices/" + responseDTO.getDeviceId()))
                        .body(responseDTO);
            }
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing device", e);
        }
    }

    @Override
    public ResponseEntity<DeviceResponseDTO> getDeviceById(String deviceId) {

        DeviceResponseDTO response = deviceService.getDeviceById(deviceId);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @Override
    public ResponseEntity<List<DeviceResponseDTO>> getDevicesByOs(String osName) {
        List<DeviceResponseDTO> response = deviceService.getDevicesByOs(osName);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(response);
    }

    @Override
    public void deleteDevice(@PathVariable String deviceId) {
        deviceService.deleteDevice(deviceId);
    }
}
