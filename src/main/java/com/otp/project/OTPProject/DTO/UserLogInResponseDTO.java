package com.otp.project.OTPProject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogInResponseDTO {
	private String userEmail;
	private String token;
	private boolean loggedIn;

}
