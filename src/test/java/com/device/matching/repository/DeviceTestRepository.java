package com.device.matching.repository;

import com.device.matching.model.Device;

import java.util.*;

public class DeviceTestRepository implements DeviceRepository {
    private final Map<String, Device> deviceStore = new HashMap<>();

    @Override
    public Device save(Device device) {
        deviceStore.put(device.getDeviceId(), device);
        return device;
    }

    @Override
    public Device updateHit(Device device) {
        Device existingDevice = deviceStore.get(device.getDeviceId());
        if (existingDevice != null) {
            existingDevice.setHitCount(existingDevice.getHitCount() + 1);
        }
        return existingDevice;
    }

    @Override
    public Optional<Device> findById(String deviceId) {
        return Optional.ofNullable(deviceStore.get(deviceId));
    }

    @Override
    public List<Device> findByOsName(String osName) {
        List<Device> devices = new ArrayList<>();
        for (Device device : deviceStore.values()) {
            if (device.getOsName().equals(osName)) {
                devices.add(device);
            }
        }
        return devices;
    }

    @Override
    public void deleteById(String deviceId) {
        deviceStore.remove(deviceId);
    }
}
