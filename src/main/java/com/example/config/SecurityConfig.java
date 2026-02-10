package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/login", "/register").permitAll()
						.requestMatchers("/exam/**", "/welcome", "/allquestions", "/question", "/check")
						.hasAuthority(Role.USER.asAuthority())
						.anyRequest().denyAll()
				)
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/welcome", true)
						.failureUrl("/login?error")       // 🔥 WAŻNE
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login")
				);

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
