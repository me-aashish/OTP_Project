package com.otp.project.otpproject.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
