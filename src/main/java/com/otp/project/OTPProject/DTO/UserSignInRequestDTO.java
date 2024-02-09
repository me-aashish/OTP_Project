package com.otp.project.OTPProject.DTO;

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
