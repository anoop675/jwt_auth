package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
    		AuthenticationException authException) throws IOException, ServletException {
   
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("Access Denied!");
        writer.println("You are not authorized to access this page\n");
        writer.println("Reason : "+authException.getMessage());
    }
    
    //Throws UNAUTHORIZED error if an unauthorized user is trying to access resource
}