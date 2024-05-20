package com.microservices.productenquiryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.microservices.productenquiryservice")
public class ProductenquiryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductenquiryserviceApplication.class, args);
	}

}
