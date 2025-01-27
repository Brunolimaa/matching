package com.device.matching.config;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AerospikeConfig {

    @Value("${aerospike.host:${AEROSPIKE_HOST:aerospike}}")  // Lê primeiro do application.properties ou, se não houver, da variável de ambiente AEROSPIKE_HOST
    private String host;

    @Value("${aerospike.port:${AEROSPIKE_PORT:3000}}")  // Lê primeiro do application.properties ou da variável de ambiente AEROSPIKE_PORT
    private int port;

    @Value("${aerospike.timeout:${AEROSPIKE_TIMEOUT:1000}}")  // Lê primeiro do application.properties ou da variável de ambiente AEROSPIKE_TIMEOUT
    private int timeout;

    @Bean
    public AerospikeClient aerospikeClient() {
        // Criação do ClientPolicy
        ClientPolicy clientPolicy = new ClientPolicy();
        clientPolicy.timeout = timeout;  // Usando a configuração de timeout do application.properties

        // Criação do host com base nas configurações do application.properties
        Host aerospikeHost = new Host(host, port);  // Usando as propriedades 'host' e 'port'

        // Retorna o cliente Aerospike
        return new AerospikeClient(clientPolicy, aerospikeHost);
    }
}