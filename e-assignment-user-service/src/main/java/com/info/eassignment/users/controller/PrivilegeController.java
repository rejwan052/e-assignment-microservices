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

import com.info.eassignment.users.persistence.model.Privilege;
import com.info.eassignment.users.service.PrivilegeService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/api/v1")
public class PrivilegeController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PrivilegeService privilegeService;
	
	
	// Get all privileges
    @GetMapping("/privileges")
    public ResponseEntity<Page<Privilege>> getAllPrivileges(@QuerydslPredicate(root = Privilege.class) Predicate predicate,
                                                            @PageableDefault(size=10) @SortDefault.SortDefaults({
                                                            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)}) Pageable pageable){

        return privilegeService.getAllPrivilegesResponse(predicate,pageable);

    }
    
	// Get a single privilege
    @GetMapping("/privileges/{id}")
    public ResponseEntity<Privilege> getSinglePrivilege(@PathVariable Long id){
        return privilegeService.getSinglePrivilegeResponse(id);
    }

    // Create a new privilege
    @PostMapping("/privileges")
    public ResponseEntity<Privilege> createNewPrivilege(@Valid @RequestBody Privilege privilege, HttpServletRequest request){
        return privilegeService.createNewPrivilege(privilege, request);
    }

    // Update Privilege with PATCH
    @PatchMapping("/privileges/{id}")
    public ResponseEntity<Privilege> patchUpdatePrivilege(@PathVariable Long id, @RequestBody Privilege privilege) {
        return privilegeService.patchUpdatePrivilege(id, privilege);
    }

    // Update Privilege with PUT
    @PutMapping("/privileges/{id}")
    public ResponseEntity<Privilege> putUpdatePrivilege(@PathVariable Long id, @RequestBody Privilege privilege) {
        return privilegeService.putUpdatePrivilege(id, privilege);
    }

}
