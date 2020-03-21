package com.info.eassignment.users.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.info.eassignment.users.exception.EntityAttributeAlreadyExistsException;
import com.info.eassignment.users.exception.ResourceNotFoundException;
import com.info.eassignment.users.persistence.model.Privilege;
import com.info.eassignment.users.persistence.repository.PrivilegeRepository;
import com.info.eassignment.users.service.PrivilegeService;
import com.info.eassignment.users.utility.ApiUtils;
import com.querydsl.core.types.Predicate;

@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private PrivilegeRepository privilegeRepository;
	private ApiUtils apiUtils;
	
	public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository, ApiUtils apiUtils) {
		super();
		Assert.notNull(privilegeRepository, "PrivilegeRepository must not be null!");
	    Assert.notNull(apiUtils, "ApiUtils must not be null!");
		this.privilegeRepository = privilegeRepository;
		this.apiUtils = apiUtils;
	}

	@Override
	public ResponseEntity<Page<Privilege>> getAllPrivilegesResponse(Predicate predicate, Pageable pageable) {
		Page<Privilege> page = privilegeRepository.findAll(predicate,pageable);
        return new ResponseEntity<Page<Privilege>>(page, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Privilege> getSinglePrivilegeResponse(Long id) {
		Privilege getPrivilege = findPrivilegeIfExists(id);
		return new ResponseEntity<Privilege>(getPrivilege, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Privilege> createNewPrivilege(Privilege privilege, HttpServletRequest request) {
		if(isPrivilegeExist(privilege.getName())) {
          throw new EntityAttributeAlreadyExistsException("Privilege already exists with "+privilege.getName());
		}

	 	Privilege newPrivilege = privilegeRepository.saveAndFlush(privilege);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", privilegeUrlHelper(newPrivilege, request));

        return new ResponseEntity<Privilege>(newPrivilege, responseHeaders, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Privilege> patchUpdatePrivilege(Long id, Privilege privilegeUpdates) {
		Privilege existingPrivilege = findPrivilegeIfExists(id);
        apiUtils.merge(existingPrivilege, privilegeUpdates);
        existingPrivilege.setId(id);
        return new ResponseEntity<Privilege>(privilegeRepository.saveAndFlush(existingPrivilege),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Privilege> putUpdatePrivilege(Long id, Privilege privilegeUpdates) {
		Privilege existingPrivilege = findPrivilegeIfExists(id);
        BeanUtils.copyProperties(privilegeUpdates,existingPrivilege);
        existingPrivilege.setId(id);
        return new ResponseEntity<Privilege>(privilegeRepository.saveAndFlush(existingPrivilege),HttpStatus.OK);
	}
	
	// Non API
    private boolean isPrivilegeExist(final String name) {
        final Privilege privilege = privilegeRepository.findByNameIgnoreCase(name);
        if(null != privilege) {
            return true;
        }
        return false;
    }

    private String privilegeUrlHelper(Privilege privilege, HttpServletRequest request) {
        StringBuilder resourcePath = new StringBuilder();

        resourcePath.append(request.getRequestURL());
        resourcePath.append("/");
        resourcePath.append(privilege.getId());

        return resourcePath.toString();
    }

    private Privilege findPrivilegeIfExists(Long id) {
        return privilegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege", "id", id));
    }

}
