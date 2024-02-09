package com.otp.project.OTPProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.project.OTPProject.DTO.UserLogInRequestDTO;
import com.otp.project.OTPProject.DTO.UserLogInResponseDTO;
import com.otp.project.OTPProject.DTO.UserSignInRequestDTO;
import com.otp.project.OTPProject.DTO.UserSignInResponseDTO;
import com.otp.project.OTPProject.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<UserSignInResponseDTO> signUpUser(@RequestBody UserSignInRequestDTO userRequestDTO) {

		return ResponseEntity.ok(authService.singUpUser(userRequestDTO));
	}

	@PostMapping("/login")
	public ResponseEntity<UserLogInResponseDTO> logInUser(@RequestBody UserLogInRequestDTO userLogInRequestDTO) {
		return ResponseEntity.ok(authService.logInUser(userLogInRequestDTO));
	}

	@GetMapping("/demo")
	public ResponseEntity<String> demo() {
		return ResponseEntity.ok("Hello");
	}
}
