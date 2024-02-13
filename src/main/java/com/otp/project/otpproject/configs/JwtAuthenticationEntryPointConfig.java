package com.otp.project.otpproject.configs;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPointConfig implements AuthenticationEntryPoint {

	// entry point of auth, return uauthorized id not logged/signed in 
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("application/json");
		response.getWriter()
				.write("{ \"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\" }");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	}
}
