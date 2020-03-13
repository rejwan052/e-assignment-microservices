package com.info.eassignment.users.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.info.eassignment.users.persistence.model.JwtRefreshToken;


@Repository
public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String> {

}
