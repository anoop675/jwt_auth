package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.security;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.service.JwtService;

@Component
public final class OpenPolicyAgentAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
	
	@Autowired
	JwtService jwtService;
	
	@Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        // Get the Authentication object
        Authentication auth = authentication.get();

        // Check if the Authentication object is of type UsernamePasswordAuthenticationToken
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            // Cast the Authentication object to UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) auth;

            // Get the principal (authenticated user)
            UserDetails userDetails = (UserDetails) usernamePasswordAuthenticationToken.getPrincipal();

            // Extract the username from the JWT authorization header
            String jwtUsername = jwtService.extractUsername(context.getRequest().getHeader(HttpHeaders.AUTHORIZATION).substring(7));

            // Compare the authenticated username with the JWT username
            if (userDetails.getUsername().equals(jwtUsername)) {
                return new AuthorizationDecision(true);
            }
        }

        return new AuthorizationDecision(false);
    }
	/*
	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
		
		 // Obtain the authenticated user's authorities
        Authentication auth = authentication.get();
        if (auth != null && auth.isAuthenticated()) {
        
            if (auth.getAuthorities().stream()
                    .anyMatch(authority -> "ROLE_USER".equals(authority.getAuthority()))) {
                // Allow access for users with the 'ROLE_ADMIN' authority
                return new AuthorizationDecision(true);
            }
        }
        
        // Deny access by default
        return new AuthorizationDecision(false);
	}*/
}
