package com.demo.naruto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.naruto.jwt.JWTTokenGenerator;
import com.demo.naruto.models.JWTUser;

@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	JWTTokenGenerator jwtTokenGenerator;

	@PostMapping
	public String generateToken(@RequestBody final JWTUser jwtUser) {
		return jwtTokenGenerator.generate(jwtUser);
	}
}
