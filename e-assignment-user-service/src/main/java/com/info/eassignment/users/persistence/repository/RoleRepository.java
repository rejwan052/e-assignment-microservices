package com.info.eassignment.users.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.info.eassignment.users.persistence.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
