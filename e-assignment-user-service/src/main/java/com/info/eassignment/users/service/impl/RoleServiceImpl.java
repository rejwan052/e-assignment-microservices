package com.info.eassignment.users.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.info.eassignment.users.exception.EntityAttributeAlreadyExistsException;
import com.info.eassignment.users.exception.ResourceNotFoundException;
import com.info.eassignment.users.persistence.model.Role;
import com.info.eassignment.users.persistence.repository.RoleRepository;
import com.info.eassignment.users.service.RoleService;
import com.info.eassignment.users.utility.ApiUtils;
import com.querydsl.core.types.Predicate;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private RoleRepository roleRepository;
	private ApiUtils apiUtils;
	
	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository,ApiUtils apiUtils) {
		super();
		Assert.notNull(roleRepository, "RoleRepository must not be null!");
	    Assert.notNull(apiUtils, "ApiUtils must not be null!");
		this.roleRepository = roleRepository;
		this.apiUtils = apiUtils;
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(long id) {
		return roleRepository.getOne(id);
	}

	@Override
	public ResponseEntity<Page<Role>> getAllRolesResponse(Predicate predicate, Pageable pageable) {
		Page<Role> page = roleRepository.findAll(predicate,pageable);
        return new ResponseEntity<Page<Role>>(page, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Role> getSingleRoleResponse(Long id) {
		Role getRole = findRoleIfExists(id);
		return new ResponseEntity<Role>(getRole, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Role> createNewRole(Role role, HttpServletRequest request) {
		if(isRoleNameExist(role.getName())) {
          throw new EntityAttributeAlreadyExistsException("Role already exists with "+role.getName());
		}

		Role newRole = roleRepository.saveAndFlush(role);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", roleUrlHelper(newRole, request));

        return new ResponseEntity<Role>(newRole, responseHeaders, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Role> patchUpdateRole(Long id, Role roleUpdates) {
		Role existingRole = findRoleIfExists(id);
        apiUtils.merge(existingRole, roleUpdates);
        existingRole.setId(id);
        return new ResponseEntity<Role>(roleRepository.saveAndFlush(existingRole),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Role> putUpdateRole(Long id, Role roleUpdates) {
		Role existingRole = findRoleIfExists(id);
        BeanUtils.copyProperties(roleUpdates,existingRole);
        existingRole.setId(id);
        return new ResponseEntity<Role>(roleRepository.saveAndFlush(existingRole),HttpStatus.OK);
	}
	
	// Non API
    private boolean isRoleNameExist(final String name) {
        final Role role = roleRepository.findByNameIgnoreCase(name);
        if(null != role) {
            return true;
        }
        return false;
    }

    private String roleUrlHelper(Role role, HttpServletRequest request) {
        StringBuilder resourcePath = new StringBuilder();

        resourcePath.append(request.getRequestURL());
        resourcePath.append("/");
        resourcePath.append(role.getId());

        return resourcePath.toString();
    }

    private Role findRoleIfExists(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
    }

}
