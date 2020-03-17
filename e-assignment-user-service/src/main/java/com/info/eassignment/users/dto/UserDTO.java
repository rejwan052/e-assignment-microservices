package com.info.eassignment.users.dto;

import java.util.List;

import com.info.eassignment.users.persistence.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private Long id;

	private String name;

	private String password;

	private String matchingPassword;

	private String username;
	
	private String email;

	private Character gender;

	private String bloodGroup;

	private List<Role> roles;

	private String organizationName;

	private String organizationDescription;

	private boolean enabled;

}
