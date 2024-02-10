package com.otp.project.OTPProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otp.project.OTPProject.DTO.OtpResponseDTO;
import com.otp.project.OTPProject.utlis.EmailUtil;
import com.otp.project.OTPProject.utlis.GenerateOtpUtil;

import jakarta.mail.MessagingException;

@Service
public class OtpService {

	@Autowired
	JwtService jwtService;

	@Autowired
	GenerateOtpUtil generateOtpUtil;

	@Autowired
	EmailUtil emailUtil;

	public OtpResponseDTO generateOtp(String token) {

		try {
			String email = jwtService.extractUserEmail(token);
			String otp = generateOtpUtil.generateOtp();
			emailUtil.sendOtpEmail(email, otp);
			OtpResponseDTO otpResponseDTO = new OtpResponseDTO();
			otpResponseDTO.setMessage("otp sent to registered email");

			return otpResponseDTO;
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp, please try again");
		}
	}
}
