package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.LoginRequest;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.RegisterRequest;

import jakarta.servlet.http.Cookie;

@Service
public class ViewRenderingService {
	
	@Autowired
	AuthenticationService authService;
	
	public ModelAndView registerUser(RegisterRequest request) {
		
		ModelAndView mv;
		
		try {
			if(request.getUsername().equals("") || request.getPassword().equals("")) {
				throw new BadCredentialsException("Bad Credentials"); 
			}

			authService.register(request);
			
			mv = new ModelAndView("/UserRegisterSuccessPage.jsp");
		}
		catch(RuntimeException e) {
			e.printStackTrace();
			mv = new ModelAndView("/User403RegisterError.jsp");
		}
		
		return mv;
		
	}
	
	public ModelAndView loginUser(LoginRequest request) {
		
		ModelAndView mv;
		
		try {
			if(request.getUsername().equals("") || request.getPassword().equals("")) {
				throw new BadCredentialsException("Bad Credentials");
			}
			
			String jwtToken = authService.login(request);
			
			mv = new ModelAndView("/redirect.jsp");
			mv.addObject("jwtToken", jwtToken);
		}
		catch(RuntimeException e) {
			e.printStackTrace();
			mv = new ModelAndView("/User403LoginError.jsp");
		}
		
		return mv;
	}
}
