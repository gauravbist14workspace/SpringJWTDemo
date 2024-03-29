package com.demo.naruto.jwt;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.demo.naruto.models.JWTUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenGenerator {

	@Value("${secret.key}")
	private String secretKey;
	
	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds; // 1h
	 
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String generate(JWTUser jwtUser) {

		Claims claims = Jwts.claims()
				.setSubject(jwtUser.getUsername());
		
		claims.put("userId", jwtUser.getUserId());
		claims.put("userRole", jwtUser.getUserRole());
		
//		Date now = new Date();
//	    Date validity = new Date(now.getTime() + validityInMilliseconds);
	    
		return Jwts.builder()
			.setClaims(claims)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	
	}

}
