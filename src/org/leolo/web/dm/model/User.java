package org.leolo.web.dm.model;

import java.util.HashSet;
import java.util.Set;

public class User {
	
	private String userName;
	private int userId;
	private String password;
	
	private Set<String> roles = new HashSet<>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}
	
}
