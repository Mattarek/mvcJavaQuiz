package com.example.repository;

import com.example.dto.RegisterUserDto;
import com.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

	private final UserService userService;

	public RegisterController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public String registerForm(final Model model) {
		model.addAttribute("user", new RegisterUserDto());
		return "register";
	}

	@PostMapping("/register")
	public String register(final RegisterUserDto registerUserDto) {
		userService.register(registerUserDto);
		return "redirect:/login";
	}
}
