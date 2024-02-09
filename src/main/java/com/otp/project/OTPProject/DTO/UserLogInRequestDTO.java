package com.otp.project.OTPProject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogInRequestDTO {
	private String userEmail;
	private String userPassword;
}
