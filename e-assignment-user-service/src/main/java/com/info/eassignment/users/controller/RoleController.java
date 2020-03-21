package com.info.eassignment.users.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.info.eassignment.users.persistence.model.Role;
import com.info.eassignment.users.service.RoleService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private RoleService roleService;
	
	
	// Get all roles
    @GetMapping("/roles")
    public ResponseEntity<Page<Role>> getAllRoles(@QuerydslPredicate(root = Role.class) Predicate predicate,
                                                            @PageableDefault(size=10) @SortDefault.SortDefaults({
                                                            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)}) Pageable pageable){

        return roleService.getAllRolesResponse(predicate,pageable);

    }
    
	// Get a single role
    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getSingleRole(@PathVariable Long id){
        return roleService.getSingleRoleResponse(id);
    }

    // Create a new role
    @PostMapping("/roles")
    public ResponseEntity<Role> createNewRole(@Valid @RequestBody Role role, HttpServletRequest request){
        return roleService.createNewRole(role, request);
    }

    // Update Role with PATCH
    @PatchMapping("/roles/{id}")
    public ResponseEntity<Role> patchUpdateRole(@PathVariable Long id, @RequestBody Role role) {
        return roleService.patchUpdateRole(id, role);
    }

    // Update Role with PUT
    @PutMapping("/roles/{id}")
    public ResponseEntity<Role> putUpdateRole(@PathVariable Long id, @RequestBody Role role) {
        return roleService.putUpdateRole(id, role);
    }

}
