package com.info.eassignment.students.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
	
	@GetMapping("/students")
    public String status() {
        return "Student service working...";
    }

}
