package com.microservice.circuitbreakerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigurationBeans {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
