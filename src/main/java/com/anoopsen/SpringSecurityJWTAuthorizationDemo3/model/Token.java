package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_token_table")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "token_id")
	public Integer id;
	
	@Column(name = "token_user")
	public String token;
	
	public Boolean revoked;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "token_type")
	public TokenType tokenType;/* = TokenType.BEARER;*/
	
	public Boolean expired;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User user; 
}
