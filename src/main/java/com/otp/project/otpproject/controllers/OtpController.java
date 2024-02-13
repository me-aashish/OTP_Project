package com.otp.project.otpproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otp.project.otpproject.dto.OtpRequestDTO;
import com.otp.project.otpproject.dto.OtpResponseDTO;
import com.otp.project.otpproject.services.OtpService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class OtpController {

	private final OtpService otpService;

	@GetMapping("/generateOTP")
	public ResponseEntity<OtpResponseDTO> generateOtp(@RequestHeader("Authorization") String authorizationHeader) {
		try {

			return new ResponseEntity<>(otpService.generateOtp(authorizationHeader), HttpStatus.OK);
		} catch (Exception e) {
			OtpResponseDTO otpResponseDTO = new OtpResponseDTO();
			otpResponseDTO.setMessage(e.getMessage());
			return new ResponseEntity<>(otpResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/documentDownload")
	public ResponseEntity<OtpResponseDTO> validateOtpAndDownloadDocument(@RequestBody OtpRequestDTO otpRequestDTO,
			HttpServletResponse response) {
		try {

			return new ResponseEntity<>(otpService.validateOtpAndDownloadDocument(otpRequestDTO, response),
					HttpStatus.OK);
		} catch (Exception e) {
			OtpResponseDTO otpResponseDTO = new OtpResponseDTO();
			otpResponseDTO.setMessage(e.getMessage());
			return new ResponseEntity<>(otpResponseDTO, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/validateOTP")
	public ResponseEntity<OtpResponseDTO> validateOtp(@RequestBody OtpRequestDTO otpRequestDTO) {
		try {

			return new ResponseEntity<>(otpService.validateOtp(otpRequestDTO), HttpStatus.OK);
		} catch (Exception e) {
			OtpResponseDTO otpResponseDTO = new OtpResponseDTO();
			otpResponseDTO.setMessage(e.getMessage());
			return new ResponseEntity<>(otpResponseDTO, HttpStatus.BAD_REQUEST);
		}
	}
}
