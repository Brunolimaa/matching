package com.device.matching.model;

import lombok.Data;


import com.aerospike.client.Bin;
import com.aerospike.client.Record;

import java.util.ArrayList;
import java.util.List;

@Data
public class Device {

    private String deviceId;
    private int hitCount;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
    private boolean isCreated;

    public List<Bin> toBins() {
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin("deviceId", this.deviceId));
        bins.add(new Bin("hitCount", this.hitCount));
        bins.add(new Bin("osName", this.osName));
        bins.add(new Bin("osVersion", this.osVersion));
        bins.add(new Bin("browserName", this.browserName));
        bins.add(new Bin("browserVersion", this.browserVersion));
        return bins;
    }

    public static Device fromRecord(Record record) {
        Device device = new Device();
        device.setDeviceId(((String) record.getValue("deviceId"))
                .trim()
                .replaceAll("\\s+", ""));
        device.setHitCount(Integer.parseInt(record.getValue("hitCount").toString()));
        device.setOsName((String) record.getValue("osName"));
        device.setOsVersion((String) record.getValue("osVersion"));
        device.setBrowserName((String) record.getValue("browserName"));
        device.setBrowserVersion((String) record.getValue("browserVersion"));
        return device;
    }

    public void setDeviceId(String osName, String browserVersion) {
        this.deviceId = osName+ "-" + browserVersion;
    }

}

