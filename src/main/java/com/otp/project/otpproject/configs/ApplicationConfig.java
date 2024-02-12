package com.otp.project.otpproject.configs;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.otp.project.otpproject.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final UserRepository userRepository;

	@Value("${otp.length}")
	private int otpLength;

	// user details config
	@Bean
	public UserDetailsService userDetailsService() {
		return userName -> userRepository.findByUserEmail(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	// auth provider config
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());

		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	// password encoder config
	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	// auth manager config
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public Random random() {
		return new Random();
	}

	public int getOtpLength() {
		return otpLength;
	}
}
