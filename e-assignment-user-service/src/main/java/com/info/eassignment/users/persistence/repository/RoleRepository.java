package com.info.eassignment.users.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.info.eassignment.users.persistence.model.QRole;
import com.info.eassignment.users.persistence.model.Role;
import com.querydsl.core.types.dsl.StringPath;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role>, QuerydslBinderCustomizer<QRole> {

    Role findByName(String name);
    
    Role findByNameIgnoreCase(String name);
    
    void delete(Role role);

    List<Role> findAll();
    
    @Override
    default public void customize(QuerydslBindings bindings, QRole root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

}
