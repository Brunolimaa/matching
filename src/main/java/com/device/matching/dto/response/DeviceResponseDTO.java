package com.device.matching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceResponseDTO {

    private String deviceId;
    private int hitCount;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
    private boolean isCreated;

}
