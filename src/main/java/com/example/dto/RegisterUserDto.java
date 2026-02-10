package com.example.dto;

public class RegisterUserDto {
	private String username;
	private String password;
	private String confirmPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(final String confirmPssword) {
		confirmPassword = confirmPssword;
	}
}
