package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	//Random 256-bit hex encrytion-key (in real apps, use a crytographically secure random number generator)
	private static final String SECRET_KEY = "3A7B1F0C2E9D5A8F4B6E7C9A1D3F0E2C5B8D7A9F0C2E5D8A1F4B7E0C3A5D7";  //allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx
	
	private static final Integer EXPIRATION = 1000 * 60 * 24; //1 Day
	
	public String generateToken (UserDetails userDetails) {   //func used to pass any extra claims along with UserDetials input
		
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

	    // Convert authorities to a list of role strings
	    List<String> roles = authorities.stream()
	            						.map(GrantedAuthority::getAuthority)
	            						.collect(Collectors.toList());
	    

	    Map<String, Object> extraClaims = new HashMap<>();
	    extraClaims.put("roles", roles); // Add the roles as a claim in the JWT

	    return generateToken(extraClaims, userDetails);
	}
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		
		String jwtToken = Jwts.builder()
							  .setClaims(extraClaims)//adding extra claims like authorities, phone number etc..
							  .setSubject(userDetails.getUsername())//subject of user's JWT is the email of the user
							  .setIssuer("Anoop-Senthil")
							  .setIssuedAt(new Date(System.currentTimeMillis()))					 //the date when the JWT is created
							  .setExpiration(new Date(System.currentTimeMillis() * EXPIRATION))	 //expiration date of this JWT   (expiry: after 24 hrs from issue)
							  .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)				 //the secret-key is signed to the JWT, encryted using HMAC-SHA256 algo
							  .compact()															 //converts JWT into a safe-URL string
							  ;

		return jwtToken;
	}
	
	public boolean isTokenValid(String jwtToken, UserDetails userDetails) { 
		
		//compares and validates if the username from token is same as username of the User from database
		final String usernameFromToken = this.extractUsername(jwtToken);
		final String usernameFromUserDetailsInput = userDetails.getUsername();
		
		return (usernameFromToken.equals(usernameFromUserDetailsInput) && !isTokenExpired(jwtToken));
	
	}
	
	private boolean isTokenExpired(String jwtToken) {
		return extractExpiration(jwtToken).before(new Date());
	}
	
	private Date extractExpiration(String jwtToken) {
		
		Function<Claims, Date> getExpirationLambdaExp = Claims::getExpiration; //or  expirationOfClaim -> Claims.EXPIRATION   extracts expiration date of type Date, of user from token
		return extractClaim(jwtToken, getExpirationLambdaExp);
	
	}
	
	public String extractUsername(String jwtToken) {
		
		Function<Claims, String> getSubjectLambdaExp = Claims::getSubject; //or  subjectOfClaim -> Claims.SUBJECT   extracts username of user from token
		return extractClaim(jwtToken, getSubjectLambdaExp);
	
	}
	
	public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
		
		final Claims claims = extractAllClaims(jwtToken);
		return claimsResolver.apply(claims);
	
	}
	
	private Claims extractAllClaims(String jwtToken) {
		
		Claims jwtTokenClaims = Jwts.parserBuilder()
						   			.setSigningKey(this.getSignInKey())   //first the server has to decode the JWT, in-order to extract the claims
						   			.build()
						   			.parseClaimsJws(jwtToken)  //extracting claims
						   			.getBody()
						   			;
		
		return jwtTokenClaims;
	}
	
	private Key getSignInKey() {
		
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);    //keyBytes will contain the decoded value of the encoded secret-key string specified
		Key secretKey = Keys.hmacShaKeyFor(keyBytes);                  //generates a secret key for use in HMAC - SHA (hash-encyption algo) operations
		return secretKey;
	
	}
}
