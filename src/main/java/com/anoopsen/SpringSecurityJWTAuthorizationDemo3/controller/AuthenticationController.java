package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.LoginRequest;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.RegisterRequest;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.service.AuthenticationService;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.service.ViewRenderingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationController {
	/*
	public ResponseEntity<String> registerUser(RegisterRequest request){
		
		String jwtToken = authService.register(request);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + jwtToken);
		
		System.out.println("JWT token in Authorization Header: "+httpHeaders.toString());
		//return ResponseEntity.ok(jwtToken);
		return ResponseEntity.ok().headers(httpHeaders).body("Registration Successful! JWT added to response header of this response");
	
	}*/
	/*
	public ResponseEntity<String> loginUser(LoginRequest request){

		String jwtToken = authService.login(request);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + jwtToken);
		
		System.out.println("JWT token in Authorization Header: "+httpHeaders.toString());
		//return ResponseEntity.ok(jwtToken);
		return ResponseEntity.ok().headers(httpHeaders).body("Authentication Successful! JWT added to response header of this response");
	}*/
	
	@Autowired
	ViewRenderingService viewService;

	@PostMapping(value = "/api/v1/auth/register")
	public ModelAndView registerUser(RegisterRequest request) {
		return viewService.registerUser(request); 
	}
	
	@PostMapping(value = "/api/v1/auth/login")
	public ModelAndView loginUser(LoginRequest request) {
		return viewService.loginUser(request);
	}
}
