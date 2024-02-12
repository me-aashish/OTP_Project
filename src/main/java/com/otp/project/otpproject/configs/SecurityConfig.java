package com.otp.project.otpproject.configs;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthFilterConfig jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationEntryPointConfig jwtAuthenticationEntryPoint;

	// security chain config for handling the type of incoming requests using auth entry point, request matchers etc.
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(withDefaults()).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers("/api/v1/auth/**", "/api/v1/generateOTP", "/api/v1/documentDownload")
						.permitAll().anyRequest().authenticated())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
