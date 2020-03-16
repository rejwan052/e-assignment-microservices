package com.info.eassignment.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.info.eassignment.users.persistence.model.User;

public interface UserService {
	
	Page<User> findAll(Pageable pageable);

}
