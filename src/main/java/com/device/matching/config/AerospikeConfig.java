package com.device.matching.config;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AerospikeConfig {

    @Value("${aerospike.host:${AEROSPIKE_HOST:aerospike}}")
    private String host;

    @Value("${aerospike.port:${AEROSPIKE_PORT:3000}}")
    private int port;

    @Value("${aerospike.timeout:${AEROSPIKE_TIMEOUT:1000}}")
    private int timeout;

    @Bean
    public AerospikeClient aerospikeClient() {
        ClientPolicy clientPolicy = new ClientPolicy();
        clientPolicy.timeout = timeout;

        Host aerospikeHost = new Host(host, port);

        return new AerospikeClient(clientPolicy, aerospikeHost);
    }
}
