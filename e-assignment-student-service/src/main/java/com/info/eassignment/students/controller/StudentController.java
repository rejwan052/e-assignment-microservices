package com.info.eassignment.students.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
	
	@Autowired
	private Environment env;
	
	@GetMapping("/students")
    public String status() {
        return "Student service working..."+env.getProperty("local.server.port");
    }

}
