package com.info.eassignment.users.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.info.eassignment.users.dto.UserDTO;
import com.info.eassignment.users.persistence.model.Privilege;
import com.info.eassignment.users.persistence.model.Role;

public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -1054602831147805200L;

	private UserDTO user;

	public AuthenticatedUser(UserDTO user) {
		super(user.getEmail(),user.getPassword(),user.isEnabled(),true,true,true,getAuthorities(user.getRoles()));
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }
	
	private static List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<String>();
        final List<Privilege> collection = new ArrayList<Privilege>();
        for (final Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
