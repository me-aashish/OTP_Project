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

	// get otp length
	@Value("${otp.length}")
	private int otpLength;

	// get otp expiration time from application.yaml file
	@Value("${otp.expiration_time_in_minutes}")
	private int otpExpirationTimeInMinutes;

	@Value("${encryption.key}")
	private String encryptionKey;

	// get jwt token expiration time
	@Value("${jwtToken.expiration_time_in_minutes}")
	private int jwtTokenExpirationTimeInMinutes;

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

	public int getOtpExpirationTimeInMinutes() {
		return otpExpirationTimeInMinutes;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public int getJwtTokenExpirationTimeInMinutes() {
		return jwtTokenExpirationTimeInMinutes;
	}

}
