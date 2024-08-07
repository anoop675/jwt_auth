package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.repository.UserRepository;

@Service
public class UserDetailsServiceIMPL implements UserDetailsService{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepo.findByUsername(username)
					   .orElseThrow(
							   () -> new UsernameNotFoundException("User not found")
					   );
	}

}
