package com.demo.naruto.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private long userId;
	private String token;
	private List<GrantedAuthority> grantedAuthorities;

	public JWTUserDetails(String username, long userId, String token, List<GrantedAuthority> grantedAuthorities) {
		this.userId = userId;
		this.username = username;
		this.token = token;
		this.grantedAuthorities = grantedAuthorities;
	}

	public long getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	public List<GrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
