package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>{

	 @Query(value = """
		      select t from Token t inner join User u\s
		      on t.user.id = u.id\s
		      where u.id = :id and (t.expired = false or t.revoked = false)\s
		      """)
	 Optional<Token> findAllValidTokenByUser(Integer id);
}
