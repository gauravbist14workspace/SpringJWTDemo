package com.demo.naruto.jwt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.demo.naruto.models.JWTAuthenticationToken;
import com.demo.naruto.models.JWTUser;
import com.demo.naruto.models.JWTUserDetails;

@Component
public class JWTAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	JWTValidator jwtValidator;
	
	@Override
	public boolean supports(Class<?> authentication) {
		return JWTAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	protected JWTUserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		JWTAuthenticationToken jwtAuthenticationToken = (JWTAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();
		
		JWTUser jwtUser = jwtValidator.validate(token);
		
		if(jwtUser == null) {
			SecurityContextHolder.clearContext();
			throw new RuntimeException("JWT Token is inccorrect!!");
		}
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(jwtUser.getUserRole());
		
//		List<GrantedAuthority> grantedAuthorities1 = Arrays.asList(jwtUser.getUserRole().split(","));
				
		return new JWTUserDetails(jwtUser.getUsername(), jwtUser.getUserId(), token, grantedAuthorities);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

	}

}
