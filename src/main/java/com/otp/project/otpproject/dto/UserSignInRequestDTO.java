package com.otp.project.otpproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInRequestDTO {
	private String userEmail;
	private String userName;
	private String userPassword;
}
