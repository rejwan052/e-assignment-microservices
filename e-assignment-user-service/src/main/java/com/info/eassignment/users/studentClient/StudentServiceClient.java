package com.info.eassignment.users.studentClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "students-service/api/v1", fallbackFactory = StudentsFallBackFactory.class)
public interface StudentServiceClient {

	@GetMapping("/students")
	public String getStudents();

}

@Component
class StudentsFallBackFactory implements FallbackFactory<StudentServiceClient> {

	@Override
	public StudentServiceClient create(Throwable cause) {
		return new StudentServiceClientFallBack(cause);
	}

}

class StudentServiceClientFallBack implements StudentServiceClient {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Throwable cause;

	public StudentServiceClientFallBack(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public String getStudents() {

		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 error took place when getStudents was called. Error message: " + cause.getLocalizedMessage());
		} else {
			logger.error("Other error took place: " + cause.getLocalizedMessage());
		}

		return "Students fall back...";
	}

}
