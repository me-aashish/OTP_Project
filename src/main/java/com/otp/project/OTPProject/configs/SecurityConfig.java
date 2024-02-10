package com.otp.project.OTPProject.configs;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	JwtAuthFilterConfig jwtAuthFilter;

	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationEntryPointConfig jwtAuthenticationEntryPoint;

	// security chain config for handling the type of incoming requests using auth entry point, request matchers etc.
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		//		http.csrf().disable().authorizeHttpRequests().requestMatchers("/api/v1/auth/**").permitAll().anyRequest()
		//				.authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
		//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		//				.authenticationProvider(authenticationProvider)
		//				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable().authorizeRequests().requestMatchers("/api/v1/auth/**").permitAll()
				.requestMatchers("/api/v1/generateOTP").permitAll().anyRequest().authenticated().and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
