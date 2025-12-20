package com.example.config;

public enum Role {
	USER,
	ADMIN;

	public String asAuthority() {
		return "ROLE_" + name();
	}
}
