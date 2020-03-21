package com.info.eassignment.users.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.info.eassignment.users.persistence.model.Role;
import com.querydsl.core.types.Predicate;

public interface RoleService {

	List<Role> getAllRoles();

	Role findById(long id);
	
	ResponseEntity<Page<Role>> getAllRolesResponse(Predicate predicate, Pageable pageable);
    ResponseEntity<Role> getSingleRoleResponse(Long id);
    ResponseEntity<Role> createNewRole(Role role, HttpServletRequest request);
    ResponseEntity<Role> patchUpdateRole(Long id, Role roleUpdates);
    ResponseEntity<Role> putUpdateRole(Long id, Role roleUpdates);

}
