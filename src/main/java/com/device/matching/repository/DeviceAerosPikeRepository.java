package com.device.matching.repository;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexType;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.aerospike.client.task.IndexTask;
import com.device.matching.model.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DeviceAerosPikeRepository implements DeviceRepository {

    private static final Logger logger = LoggerFactory.getLogger(DeviceAerosPikeRepository.class);

    private final AerospikeClient client;
    @Value("${aerospike.namespace}")
    private String namespace;
    @Value("${aerospike.set}")
    private String set;

    public DeviceAerosPikeRepository(AerospikeClient client) {
        this.client = client;
    }

    @PostConstruct
    public void init() {
        createOsNameIndex();
    }

    @Override
    public Device save(Device device) {
        Key key = new Key(namespace, set, device.getDeviceId());
        client.put(null, key, device.toBins().toArray(new Bin[0]));
        return device;
    }

    @Override
    public Device updateHit(Device device) {
        Key key = new Key(namespace, set, device.getDeviceId());
        var hitPlusOne = device.getHitCount() + 1;
        client.put(null, key, new Bin("hitCount", hitPlusOne));
        device.setHitCount(hitPlusOne);
        return device;
    }

    @Override
    public Optional<Device> findById(String deviceId) {
        Key key = new Key(namespace, set, deviceId);
        Record record = client.get(null, key);
        return Optional.ofNullable(record).map(this::mapRecordToDevice);
    }

    @Override
    public List<Device> findByOsName(String osName) {
        List<Device> devices = new ArrayList<>();
        Statement statement = new Statement();
        statement.setNamespace(namespace);
        statement.setSetName(set);
        statement.setFilter(Filter.equal("osName", osName));

        try (RecordSet recordSet = client.query(null, statement)) {
            while (recordSet.next()) {
                devices.add(mapRecordToDevice(recordSet.getRecord()));
            }
        } catch (Exception e) {
            logger.error("Error fetching devices by OS name", e);
        }

        return devices;
    }

    @Override
    public void deleteById(String deviceId) {
        Key key = new Key(namespace, set, deviceId);
        try {
            client.delete(null, key);
        } catch (Exception e) {
            logger.error("Error deleting device with ID: " + deviceId, e);
        }
    }

    private Device mapRecordToDevice(Record record) {
        Device device = new Device();
        device.setDeviceId(record.getString("deviceId"));
        device.setOsName(record.getString("osName"));
        device.setHitCount(record.getInt("hitCount"));
        device.setOsVersion(record.getString("osVersion"));
        device.setBrowserName(record.getString("browserName"));
        device.setBrowserVersion(record.getString("browserVersion"));
        return device;
    }

    private void createOsNameIndex() {
        try {
            Policy policy = new Policy();
            IndexTask task = client.createIndex(policy, namespace, set, "osName_idx", "osName", IndexType.STRING);
            task.waitTillComplete();
            logger.info("Index 'osName_idx' created successfully!");
        } catch (Exception e) {
            logger.error("Error creating index 'osName_idx'", e);
        }
    }
}
