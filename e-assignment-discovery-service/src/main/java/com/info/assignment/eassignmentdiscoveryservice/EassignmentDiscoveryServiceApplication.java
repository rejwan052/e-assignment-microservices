package com.info.assignment.eassignmentdiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EassignmentDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EassignmentDiscoveryServiceApplication.class, args);
	}

}
