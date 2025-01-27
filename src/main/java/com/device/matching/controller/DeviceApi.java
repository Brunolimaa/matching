package com.device.matching.controller;

import com.device.matching.dto.response.DeviceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/devices")
public interface DeviceApi {

    @Operation(summary = "Create or update device based on user agent",
            description = "Matches the user agent with an existing device or creates/updates a new device based on the user agent string.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device successfully matched or updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class))),
            @ApiResponse(responseCode = "201", description = "Device created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error while processing the device")
    })
    @PostMapping("/match")
    ResponseEntity<DeviceResponseDTO> matchDevice(
            @Parameter(description = "User agent string sent by the client to match or create/update the device")
            @RequestBody String userAgent
    );

    @Operation(summary = "Get device by ID",
            description = "Fetches a device by its unique identifier (deviceId).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device found and returned"),
            @ApiResponse(responseCode = "404", description = "Device not found for the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error while processing the device")
    })
    @GetMapping("/{deviceId}")
    ResponseEntity<DeviceResponseDTO> getDeviceById(
            @Parameter(description = "The unique identifier of the device")
            @PathVariable String deviceId
    );

    @Operation(summary = "Get devices by operating system",
            description = "Returns a list of devices filtered by the operating system name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices found"),
            @ApiResponse(responseCode = "404", description = "No devices found for the provided operating system")
    })
    @GetMapping("/os/{osName}")
    ResponseEntity<List<DeviceResponseDTO>> getDevicesByOs(
            @Parameter(description = "Operating system name to filter devices")
            @PathVariable String osName
    );

    @Operation(summary = "Delete a device",
            description = "Deletes a device by its unique identifier (deviceId).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Device not found for the given ID")
    })
    @DeleteMapping("/{deviceId}")
    void deleteDevice(
            @Parameter(description = "The unique identifier of the device to be deleted")
            @PathVariable String deviceId
    );

}
