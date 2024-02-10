package com.otp.project.OTPProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.project.OTPProject.DTO.OtpResponseDTO;
import com.otp.project.OTPProject.services.OtpService;

@RestController
@RequestMapping("/api/v1/")
public class OtpController {

	@Autowired
	OtpService otpService;

	@GetMapping("/generateOTP")
	public ResponseEntity<OtpResponseDTO> generateOtp(@RequestHeader("Authorization") String authorizationHeader) {
		try {
			String token = authorizationHeader.substring(7);
			return new ResponseEntity<>(otpService.generateOtp(token), HttpStatus.OK);
		} catch (Exception e) {
			OtpResponseDTO otpResponseDTO = new OtpResponseDTO();
			otpResponseDTO.setMessage(e.getMessage());
			return new ResponseEntity<>(otpResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
