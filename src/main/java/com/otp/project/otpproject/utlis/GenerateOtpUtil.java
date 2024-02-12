package com.otp.project.otpproject.utlis;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.otp.project.otpproject.configs.ApplicationConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenerateOtpUtil {

	private final ApplicationConfig applicationConfig;
	private final Random random;

	public String generateOtp() {

		// getting otp length from application config
		int otpLength = applicationConfig.getOtpLength();

		// setting limit for generatiog random number and using string and temporary variable
		StringBuilder maximumNumberStringBuilder = new StringBuilder();
		int tempLength = otpLength;

		while (tempLength > 0) {
			maximumNumberStringBuilder.append("9");
			tempLength--;
		}
		String maximumNumberString = maximumNumberStringBuilder.toString();
		int maximumNumberInt = Integer.parseInt(maximumNumberString);

		int randomNumber = random.nextInt(maximumNumberInt);
		String output = Integer.toString(randomNumber);

		/**
		 * if random generated number will be 0012, 
		 * it'll get converted to 12,
		 *  so to make it 0012, add remaining 0s.
		 */
		if (output.length() < otpLength) {
			StringBuilder stringBuilder = new StringBuilder(otpLength);
			for (int i = 0; i < otpLength - output.length(); i++) {
				stringBuilder.append("0");
			}
			stringBuilder.append(output);
			output = stringBuilder.toString();
		}

		return output;
	}
}
