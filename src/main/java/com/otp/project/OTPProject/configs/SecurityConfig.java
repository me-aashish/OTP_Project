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
	JwtAuthFilter jwtAuthFilter;

	private final AuthenticationProvider authenticationProvider;

	private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
			"/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html" };

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//
		//		http.csrf(AbstractHttpConfigurer::disable)
		//				.authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL).permitAll().requestMatchers("")
		//						.hasAnyRole(ADMIN.name()).anyRequest().authenticated())
		//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		//				.authenticationProvider(authenticationProvider)
		//				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable().authorizeHttpRequests().requestMatchers("/api/v1/auth/**").permitAll().anyRequest()
				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
