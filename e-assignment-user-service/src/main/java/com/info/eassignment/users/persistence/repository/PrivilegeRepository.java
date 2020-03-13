package com.info.eassignment.users.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.info.eassignment.users.persistence.model.Privilege;


@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

}