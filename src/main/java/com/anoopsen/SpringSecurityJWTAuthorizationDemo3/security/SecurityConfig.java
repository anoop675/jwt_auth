package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.service.UserDetailsServiceIMPL;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
/*
@EnableGlobalMethodSecurity(
		prePostEnabled = false, securedEnabled = false, jsr250Enabled = true
)*/
//@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	@Autowired
	private UserDetailsServiceIMPL userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthorizationManager<RequestAuthorizationContext> customAuthorization) throws Exception{
		
		http
				.csrf(csrfToken -> csrfToken.disable())
				.authorizeHttpRequests( httpRequests -> httpRequests
							
										//.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR) 
										//.permitAll() //dispatcher servlets to render views (FORWARD) and errors (ERROR) are permitted
										.requestMatchers(
												new AntPathRequestMatcher("/api/v1/view/**"),
												new AntPathRequestMatcher("/UserLogin.jsp"),
												new AntPathRequestMatcher("/UserRegister.jsp"),
												new AntPathRequestMatcher("/UserRegisterSuccessPage.jsp"),
												new AntPathRequestMatcher("/User403LoginError.jsp"),
												new AntPathRequestMatcher("/User403RegisterError.jsp"),
												new AntPathRequestMatcher("/redirect.jsp")
										)
										.permitAll()
										
										.requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**"))
										.permitAll()
										
										/*
										.requestMatchers(
												new AntPathRequestMatcher("/api/v1/resource/**"),
												new AntPathRequestMatcher("/Index.jsp")
												
										)
										//.access(customAuthorization) // Custom access control
										//.access("hasRole('USER')")
										//.hasRole("USER")
										//.permitAll()
										.authenticated()*/
										
										.anyRequest()
										.authenticated()
										
										
				)/*
				.exceptionHandling(
						ex -> ex.authenticationEntryPoint(entryPoint)
								//.accessDeniedPage("/api/v1/view/login/error")
				)*/
				//every request coming to the filterchain must be authenticated, so we will not make use of session id as stateful in server
				//JWT is possible with stateless sessions 
				.sessionManagement( 
						session -> session
										.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.authenticationProvider(this.customDaoAuthenticationProvider())
				//.authenticationManager(this.customAuthenticationManager(http))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
				
				/*
				.formLogin(
						form -> form.loginPage("/api/v1/view/login")
						            //.loginProcessingUrl("/api/v1/auth/login")
						            
				
				);*/
				
		DefaultSecurityFilterChain filterChain = http.build();
		return filterChain;
			
	}
	
	@Bean
    public AuthenticationProvider customDaoAuthenticationProvider() {
		
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); 
        
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(this.passwordEncoder());
        
        return authProvider;
    }
	
	/*
	@Bean
    public AuthenticationManager customAuthenticationManager(HttpSecurity http) throws Exception {
		
        AuthenticationManagerBuilder authenticationManagerBuilder = 
        		http.getSharedObject(AuthenticationManagerBuilder.class);
        
        //adding authentication providers
        authenticationManagerBuilder.authenticationProvider(this.customDaoAuthenticationProvider());
        AuthenticationManager authManager = authenticationManagerBuilder.build(); 

        return authManager; 
	}*/
	
	
	@Bean
	public AuthenticationManager customAuthenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(16);
	}
}
