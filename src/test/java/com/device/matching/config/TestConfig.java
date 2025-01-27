package com.device.matching.config;

import com.aerospike.client.AerospikeClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestConfig {

    @MockBean
    private AerospikeClient aerospikeClient;  // Mock do AerospikeClient

}