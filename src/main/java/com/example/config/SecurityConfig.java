package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/login").permitAll()
						.requestMatchers("/exam/**", "/allquestions", "/question", "/check").hasAuthority(Role.USER.asAuthority())
						.anyRequest().denyAll()
				)
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/")
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
				);

		return httpSecurity.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				User.withUsername("marcin")
						.password(passwordEncoder().encode("1234"))
						.authorities(Role.USER.asAuthority())
						.build()
		);
	}
}
