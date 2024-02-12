package com.otp.project.otpproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.project.otpproject.dto.UserLogInRequestDTO;
import com.otp.project.otpproject.dto.UserLogInResponseDTO;
import com.otp.project.otpproject.dto.UserSignInRequestDTO;
import com.otp.project.otpproject.dto.UserSignInResponseDTO;
import com.otp.project.otpproject.exceptions.UserAlreadyExistsException;
import com.otp.project.otpproject.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<UserSignInResponseDTO> signUpUser(@RequestBody UserSignInRequestDTO userRequestDTO) {
		try {
			// register new user if it not exists
			UserSignInResponseDTO userSignInResponseDTO = authService.singUpUser(userRequestDTO);
			return new ResponseEntity<>(userSignInResponseDTO, HttpStatus.CREATED);
		} catch (UserAlreadyExistsException e) {
			// if user already exists throw the exception
			UserSignInResponseDTO userSignInResponseDTO = new UserSignInResponseDTO();
			userSignInResponseDTO.setMessage(e.getMessage());
			return new ResponseEntity<>(userSignInResponseDTO, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<UserLogInResponseDTO> logInUser(@RequestBody UserLogInRequestDTO userLogInRequestDTO) {

		try {
			// log in the user with good credentials
			UserLogInResponseDTO userLogInResponseDTO = authService.logInUser(userLogInRequestDTO);
			return new ResponseEntity<>(userLogInResponseDTO, HttpStatus.OK);
		} catch (Exception e) {
			// throw exception if credentials are bad
			UserLogInResponseDTO userLogInResponseDTO = new UserLogInResponseDTO();
			userLogInResponseDTO.setMessage(e.getMessage());
			return new ResponseEntity<>(userLogInResponseDTO, HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/demo")
	public ResponseEntity<String> demo() {
		return ResponseEntity.ok("Hello");
	}
}
