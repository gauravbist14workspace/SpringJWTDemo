package com.demo.naruto.security;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {

	private JWTAuthenticationProvider jwtAuthenticationProvider;
	private JWTAuthenticationEntryPoint entryPoint;
	
	// Spring uses by default LDAP filters
	// We're going to replace with custom JWT filters
	@Bean
	public JwtAuthenticationTokenFilter authenticationFilter() {
		JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
		
		return filter;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		super.configure(http);
		
		http.csrf().disable()
			.authorizeRequests().antMatchers("**/rest/**").authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(entryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http.headers().cacheControl();
	}
	
}
