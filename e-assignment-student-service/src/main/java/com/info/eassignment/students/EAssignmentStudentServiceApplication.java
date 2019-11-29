package com.info.eassignment.students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EAssignmentStudentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAssignmentStudentServiceApplication.class, args);
	}

}
