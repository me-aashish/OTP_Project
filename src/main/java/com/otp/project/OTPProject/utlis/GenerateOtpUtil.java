package com.otp.project.OTPProject.utlis;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.otp.project.OTPProject.configs.ApplicationConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenerateOtpUtil {

	private final ApplicationConfig applicationConfig;

	public String generateOtp() {

		// getting otp length from application config
		int otpLength = applicationConfig.getOtpLength();

		// using random class to generate random numbers
		Random random = new Random();

		// setting limit for generatiog random number and using string and temporary variable
		String maximumNumberString = "";
		int tempLength = otpLength;

		while (tempLength > 0) {
			maximumNumberString += "9";
			tempLength--;
		}

		int maximumNumberInt = Integer.parseInt(maximumNumberString);

		int randomNumber = random.nextInt(maximumNumberInt);
		String output = Integer.toString(randomNumber);

		/**
		 * if random generated number will be 0012, 
		 * it'll get converted to 12,
		 *  so to make it 0012, add remaining 0s.
		 */
		while (output.length() < otpLength) {
			output = "0" + output;
		}

		return output;
	}
}
