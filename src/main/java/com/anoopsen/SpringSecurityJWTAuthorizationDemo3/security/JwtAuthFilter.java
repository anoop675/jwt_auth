package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.security;

import java.io.IOException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.service.JwtService;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.service.UserDetailsServiceIMPL;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	@Autowired
	UserDetailsServiceIMPL userDetailsService;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	JwtAuthenticationEntryPoint entryPoint;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain
	)throws ServletException, IOException {
		
		//printHeaders(request);   //Checking if the request has Authorization Header as it reaches JWT Auth filter
		//printAuthorizationHeader(request); //
		
		final String authorizationHeader = request.getHeader("Authorization");
		//logger.info("Header : {}", authorizationHeader);
		
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			
			//logger.info("Invalid Header value");
			System.out.println("Incoming request has NO authorization header");
			printHeaders(request);
			filterChain.doFilter(request, response);
			return;
			
		}
		System.out.println("Incoming request has authorization header");
		printHeaders(request);
		
		final int startingIndexOfJwt = 7;
		final String jwt = authorizationHeader.substring(startingIndexOfJwt);
		String userName = null;
		
		try {
			userName = jwtService.extractUsername(jwt);
		}
		catch(MalformedJwtException e) {
			e.printStackTrace();
		}
		catch(ExpiredJwtException e) {
			e.printStackTrace();
		}
		catch(UnsupportedJwtException e) {
			e.printStackTrace();
		}
		catch(SignatureException e) {
			e.printStackTrace();
		}
		
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) { //if the user associated with the userName is not authenticated
			
			UserDetails user = userDetailsService.loadUserByUsername(userName);
			
			Boolean isTokenValid = jwtService.isTokenValid(jwt, user);
				
			if(isTokenValid) {
					
				UsernamePasswordAuthenticationToken successfulAuthToken = 
						new UsernamePasswordAuthenticationToken(
								user,
								null,
								user.getAuthorities()
						);
				
				//building token using the parameters of UsernamePasswordAuthenticationToken()
				successfulAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
				//saving authenticated user-details in SecurityContextHolder
				System.out.println("saving as authenticated user...");
				//System.out.println(successfulAuthToken.toString());
				
				
				SecurityContextHolder.getContext().setAuthentication(successfulAuthToken);
				
				
				
				printAuthenticatedUser();
				
				filterChain.doFilter(request, response);
					
			}
			else {
				// Token is not valid, send an unauthorized response
				sendUnauthorizedResponse(response);
			}

		} 
		else {
			// Invalid token or user, send an unauthorized response
			sendUnauthorizedResponse(response);
		}
	}

	private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("Unauthorized");
	}
	
	
	public void printHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
     
        System.out.println("\nPRINTING REQUEST HEADERS...\n");
        
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);

            // Print or store the header name and value
            System.out.println("\t"+headerName + ": " + headerValue);
        }
    }
	
	/*
	public void printAuthorizationHeader(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader != null) {
			System.out.println("Authorization header = "+authorizationHeader);
		}
		System.out.println("No Authorization Header in received request");
	}*/
	
	public void printAuthenticatedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication != null && authentication.isAuthenticated()) {
			
			Object principal = authentication.getPrincipal();
			UserDetails user = (UserDetails) principal;
			//String username = user.getUsername();
		
			System.out.println("Authenticated User: "+user.toString());
			
		}
		else {
			System.out.println("No user is authenticated");
		}
	}
}
