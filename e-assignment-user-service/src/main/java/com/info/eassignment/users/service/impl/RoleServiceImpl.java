package com.info.eassignment.users.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.eassignment.users.persistence.model.Role;
import com.info.eassignment.users.persistence.repository.RoleRepository;
import com.info.eassignment.users.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	private RoleRepository roleRepository;
	
	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(long id) {
		return roleRepository.getOne(id);
	}

}
