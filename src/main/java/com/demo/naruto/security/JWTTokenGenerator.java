package com.demo.naruto.security;

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
	
	public String generate(JWTUser jwtUser) {

		Claims claims = Jwts.claims()
//				.setExpiration(exp)
				.setSubject(jwtUser.getUsername());
		
		claims.put("userId", jwtUser.getUserId());
		claims.put("role", jwtUser.getUserRole());
		
		return Jwts.builder()
			.setClaims(claims)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	
	}

}
