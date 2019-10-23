package com.info.eassignment.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class EAssignmentConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAssignmentConfigServerApplication.class, args);
	}

}
