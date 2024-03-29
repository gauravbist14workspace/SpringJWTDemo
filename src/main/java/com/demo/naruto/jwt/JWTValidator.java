package com.demo.naruto.jwt;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.demo.naruto.models.JWTUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTValidator {

	@Value("${secret.key}")
	private String secret;

	@PostConstruct
	protected void init() {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}

	public JWTUser validate(String token) {

		JWTUser jwtUser = null;

		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

			jwtUser = new JWTUser();
			jwtUser.setUsername(body.getSubject());
			jwtUser.setId(Long.parseLong(String.valueOf(body.get("userId"))));
			jwtUser.setRole((String) body.get("userRole"));

		} catch (Exception e) {
			System.out.println("JWTValidator: " + e.getMessage());
		}

		return jwtUser;
	}

}
