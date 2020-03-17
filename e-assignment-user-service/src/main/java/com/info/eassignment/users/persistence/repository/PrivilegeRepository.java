package com.info.eassignment.users.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.info.eassignment.users.persistence.model.Privilege;
import com.info.eassignment.users.persistence.model.QPrivilege;
import com.querydsl.core.types.dsl.StringPath;


@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>, QuerydslPredicateExecutor<Privilege>, QuerydslBinderCustomizer<QPrivilege> {

    Privilege findByName(String name);
    
    Privilege findByNameIgnoreCase(String name);
    
    void delete(Privilege privilege);
    
    @Override
    default public void customize(QuerydslBindings bindings, QPrivilege root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

}