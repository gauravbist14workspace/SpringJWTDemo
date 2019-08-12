package com.demo.naruto.models;

public class JWTUser {

	private String username;
	private long userId;
	private String userRole;

	public void setUsername(String username) {
		this.username = username;
		
	}

	public void setId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public long getUserId() {
		return userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public void setRole(String userRole) {
		this.userRole = userRole;
	}

	
}
