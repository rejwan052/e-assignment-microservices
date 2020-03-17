package com.info.eassignment.users.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.info.eassignment.users.dto.UserDTO;
import com.info.eassignment.users.persistence.model.Role;
import com.info.eassignment.users.persistence.model.User;

public class UserMapper {

	static List<UserDTO> mapEntitiesIntoDTOs(List<User> entities) {
		return entities.stream().map(UserMapper::mapEntityIntoDTO).collect(Collectors.toList());
	}

	
	public static UserDTO mapEntityIntoDTO(User entity) {
		UserDTO dto = new UserDTO();

		dto.setId(entity.getId());
		dto.setName((null != entity.getName() ? entity.getName(): ""));
		dto.setEmail(entity.getEmail());
		dto.setPassword(entity.getPassword());
		dto.setBloodGroup(entity.getBloodGroup() != null ? entity.getBloodGroup() : " ");
		dto.setGender(entity.getGender() != null ? entity.getGender() : Character.MIN_VALUE);

		List<Role> roles = new ArrayList<>(entity.getRoles());
		dto.setRoles(roles);
		
		if(entity.getOrganization() != null){
			dto.setOrganizationName(entity.getOrganization().getName());
			dto.setOrganizationDescription(entity.getOrganization().getDescription());
		}
		
		dto.setEnabled(entity.isEnabled());

		return dto;
	}

	public static Page<UserDTO> mapEntityPageIntoDTOPage(Pageable page, Page<User> source) {
		List<UserDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
		return new PageImpl<>(dtos, page, source.getTotalElements());
	}

}
