package com.device.matching.mapper;

import com.device.matching.dto.response.DeviceResponseDTO;
import com.device.matching.model.Device;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    DeviceResponseDTO deviceToDeviceResponseDTO(Device device);

    Device deviceResponseDTOToDevice(DeviceResponseDTO deviceResponseDTO);
}
