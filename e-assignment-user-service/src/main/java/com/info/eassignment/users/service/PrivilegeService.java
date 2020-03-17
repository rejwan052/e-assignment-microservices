package com.info.eassignment.users.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.info.eassignment.users.persistence.model.Privilege;
import com.querydsl.core.types.Predicate;

public interface PrivilegeService {
	
	ResponseEntity<Page<Privilege>> getAllPrivilegesResponse(Predicate predicate, Pageable pageable);
    ResponseEntity<Privilege> getSinglePrivilegeResponse(Long id);
    ResponseEntity<Privilege> createNewPrivilege(Privilege privilege, HttpServletRequest request);
    ResponseEntity<Privilege> patchUpdatePrivilege(Long id, Privilege privilegeUpdates);
    ResponseEntity<Privilege> putUpdatePrivilege(Long id, Privilege privilegeUpdates);

}
