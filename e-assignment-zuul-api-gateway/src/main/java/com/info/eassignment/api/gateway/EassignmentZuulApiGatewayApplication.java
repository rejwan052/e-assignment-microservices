package com.info.eassignment.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class EassignmentZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EassignmentZuulApiGatewayApplication.class, args);
	}

}
