package com.demo.naruto.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.demo.naruto.models.JWTAuthenticationToken;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

	public JwtAuthenticationTokenFilter() {
		super("/rest/**");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

		String header = request.getHeader("Authorisation");
		
		if(header == null || !header.startsWith("Basic")) {
			throw new RuntimeException("JWT token is missing");
		}
		
		String authenticationToken = header.split(" ")[1];
		JWTAuthenticationToken token = new JWTAuthenticationToken(authenticationToken);
		
		return getAuthenticationManager().authenticate(token);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		super.successfulAuthentication(request, response, chain, authResult);
		
		// after this filter, proceed to next filter
		chain.doFilter(request, response);
	}
}
