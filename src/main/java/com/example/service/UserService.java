package com.example.service;

import com.example.config.Role;
import com.example.dto.RegisterUserDto;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void register(final RegisterUserDto registerUserDto) {
		if (userRepository.existsByUsername(registerUserDto.getUsername())) {
			throw new IllegalArgumentException("Username already exists");
		}

		if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		final User user = new User();
		user.setUsername(registerUserDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
		user.setRole(Role.USER);

		userRepository.save(user);
	}
}
