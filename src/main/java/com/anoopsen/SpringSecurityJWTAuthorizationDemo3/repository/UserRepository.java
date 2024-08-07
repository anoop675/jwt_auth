package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	public Optional<User> findByUsername(String username);
}
