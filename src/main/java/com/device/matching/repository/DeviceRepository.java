package com.device.matching.repository;


import com.device.matching.model.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository {

    Device save(Device device);

    Device updateHit(Device device);

    Optional<Device> findById(String deviceId);

    List<Device> findByOsName(String osName);

    void deleteById(String deviceId);

}
