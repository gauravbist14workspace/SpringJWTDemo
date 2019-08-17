package com.demo.naruto.controllers;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/auth/linkedin")
public class LinkedInController {

	public static final Logger LOGGER = LoggerFactory.getLogger(LinkedInController.class);
	
	@RequestMapping(value = "/callback")
	public String linkedInAuthCallback(@RequestParam(required = false)final String code, @RequestParam(required = false)final String state,
			 final HttpServletResponse servletResponse) {
		
		LOGGER.info("Code: {}", code);
		LOGGER.trace("State: " + state);
		return "SUCCESS";
	}
}
