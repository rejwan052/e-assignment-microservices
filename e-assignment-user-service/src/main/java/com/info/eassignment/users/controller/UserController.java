package com.info.eassignment.users.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.info.eassignment.users.persistence.model.User;
import com.info.eassignment.users.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable){
    	return new ResponseEntity<Page<User>>(userService.findAll(pageable), HttpStatus.OK);
    }
	

}
