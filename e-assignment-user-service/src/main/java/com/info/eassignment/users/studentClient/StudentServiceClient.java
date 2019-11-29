package com.info.eassignment.users.studentClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "students-service/api/v1")
public interface StudentServiceClient {
	
	@GetMapping("/students")
	public String getStudents();

}
