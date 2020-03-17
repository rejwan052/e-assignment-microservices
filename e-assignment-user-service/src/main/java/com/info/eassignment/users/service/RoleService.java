package com.info.eassignment.users.service;

import java.util.List;

import com.info.eassignment.users.persistence.model.Role;

public interface RoleService {

	List<Role> getAllRoles();

	Role findById(long id);

}
