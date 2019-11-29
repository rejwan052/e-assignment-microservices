package com.info.eassignment.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.info.eassignment.users.persistence.model.User;
import com.info.eassignment.users.shared.UserDto;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserDetailsByEmail(String email);
    Page<User> findAll(Pageable pageable);
    String getStudents();
}