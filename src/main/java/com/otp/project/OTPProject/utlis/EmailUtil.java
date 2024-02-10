package com.otp.project.OTPProject.utlis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendOtpEmail(String email, String otp) throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo(email);
		mimeMessageHelper.setSubject("Your OTP for document download");
		mimeMessageHelper.setText("""
				<div>
				  <h3>Your OTP for downloading document is : %s</h3>
				</div>
				""".formatted(otp), true);

		javaMailSender.send(mimeMessage);
	}

}
