package com.otp.project.otpproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInResponseDTO {

	private String token;
	private String message;
}
