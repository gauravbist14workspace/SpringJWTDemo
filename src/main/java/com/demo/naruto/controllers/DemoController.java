package com.demo.naruto.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/hello")
public class DemoController {

	@GetMapping
	public String greetUser(final HttpServletResponse servletResponse) {
		return "Hello World";
	}
}
