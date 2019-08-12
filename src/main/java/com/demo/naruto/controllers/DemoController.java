package com.demo.naruto.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest")
public class DemoController {

	@RequestMapping(method = RequestMethod.GET, value="/hello")
	public String greetUser(final HttpServletResponse servletResponse) {
		return "Hello World";
	}
}
