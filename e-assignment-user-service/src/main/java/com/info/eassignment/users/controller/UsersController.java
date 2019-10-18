package com.info.eassignment.users.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.info.eassignment.users.payload.CreateUserRequest;
import com.info.eassignment.users.payload.CreateUserResponse;
import com.info.eassignment.users.service.UserService;
import com.info.eassignment.users.shared.UserDto;


@RestController
@RequestMapping("/api/v1")
public class UsersController {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String status() {
        return "Working... on port " + env.getProperty("local.server.port");
    }

    @PostMapping(value = "/users")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest userRequest) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userRequest, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);

        CreateUserResponse returnValue = modelMapper.map(createdUser, CreateUserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

}