package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.service;

import org.springframework.stereotype.Service;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.LoginRequest;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.RegisterRequest;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.Role;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.Token;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.TokenType;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.User;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.repository.TokenRepository;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.repository.UserRepository;
import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.security.SecurityConfig;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class AuthenticationService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private SecurityConfig config;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenRepository tokenRepo;

	public void register(RegisterRequest request) {
		
		 var user = User.builder()                           // this User is from com.anoopsen.JWTAuthDemo.model.User; which has @Builder lombok annotation
		 				.username(request.getUsername())
		 				.password(config.passwordEncoder().encode(request.getPassword()))
		 				.role(Role.USER)
		 				.build()
		 				;
		 				   
		userRepo.save(user);
		
		var jwtToken = jwtService.generateToken(user);  //Purposefully tampering the JWT for test
		
		var token = Token.builder()
			     	.token(jwtToken)
			     	.tokenType(TokenType.BEARER)
			     	.revoked(false)
			     	.expired(false)
			     	.user(user)
			     	.build()
			     	;
		
		tokenRepo.save(token);
					
		System.out.println(user.getUsername()+" registered!");
		System.out.println(user.getUsername()+"'s JWT saved in DB : "+token.getToken());
	}
	
	public String login(LoginRequest request) {
		
		var user = userRepo.findByUsername(request.getUsername()).orElseThrow();
		
		Authentication auth = new UsernamePasswordAuthenticationToken(
									request.getUsername(),
									request.getPassword(),
									user.getAuthorities()
								);
		
		authenticationManager.authenticate(auth);	            //if user cannot be authenticated then, it will throw a 403 response        
		
		var token = tokenRepo.findAllValidTokenByUser(user.getId()).orElseThrow();
		
		System.out.println(user.getUsername()+" authenticated!");
		System.out.println(user.getUsername()+"'s JWT: "+token.getToken());
		
		return token.getToken();
	}
}