package com.otp.project.OTPProject.utlis;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GenerateOtpUtil {

	public String generateOtp() {

		// using random class to generate random numbers
		Random random = new Random();
		int randomNumber = random.nextInt(9999);
		String output = Integer.toString(randomNumber);

		/**
		 * if random generated number will be 0012, 
		 * it'll get converted to 12,
		 *  so to make it 0012, add remaining 0s.
		 */
		while (output.length() < 4) {
			output = "0" + output;
		}

		return output;
	}
}
