package com.otp.project.OTPProject.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otp.project.OTPProject.DTO.OtpRequestDTO;
import com.otp.project.OTPProject.DTO.OtpResponseDTO;
import com.otp.project.OTPProject.entities.MfaValidation;
import com.otp.project.OTPProject.exceptions.InvalidOtpException;
import com.otp.project.OTPProject.repositories.MfaValidationRepsitory;
import com.otp.project.OTPProject.utlis.EmailUtil;
import com.otp.project.OTPProject.utlis.GenerateOtpUtil;

import jakarta.mail.MessagingException;

@Service
public class OtpService {

	@Autowired
	JwtService jwtService;

	@Autowired
	GenerateOtpUtil generateOtpUtil;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	MfaValidationRepsitory mfaValidationRepsitory;

	public OtpResponseDTO generateOtp(String authorizationHeader) {

		try {
			// extracting bearer token from auth header
			String token = authorizationHeader.substring(7);

			// extracting claim email from token
			String email = jwtService.extractUserEmail(token);

			// generating otp
			String otp = generateOtpUtil.generateOtp();

			// calling java mailer to send mail
			emailUtil.sendOtpEmail(email, otp);

			// setting up response
			OtpResponseDTO otpResponseDTO = new OtpResponseDTO();
			otpResponseDTO.setMessage("otp sent to registered email");

			// saving data to database
			MfaValidation mfaValidation = new MfaValidation();
			mfaValidation.setUserEmail(email);
			mfaValidation.setOtp(Integer.parseInt(otp));
			mfaValidationRepsitory.save(mfaValidation);

			// return the response
			return otpResponseDTO;
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp, please try again");
		}
	}

	public OtpResponseDTO validateOtpAndDownloadDocument(String authorizationHeader, OtpRequestDTO otpRequestDTO)
			throws InvalidOtpException {

		// get the entered otp
		int otp = otpRequestDTO.getOtp();

		// extract token from auth header
		String token = authorizationHeader.substring(7);

		// extracting claim email from token
		String email = jwtService.extractUserEmail(token);

		//		MfaValidation otpObject= mfaValidationRepsitory.getByUserEmail(email);

		List<MfaValidation> listMfaValidationList = mfaValidationRepsitory.getOtpList(email);
		boolean flag = false;
		//		listMfaValidationList.forEach(Element -> {
		//			MfaValidation otpObject = Element;
		//			int storedOtp = otpObject.getOtp();
		//			//throw new InvalidOtpException("Wrong OTP entered");
		////			if(storedOtp != otp) {
		////				flag = true;
		////				return;
		////			}
		//			
		//			if(otpObject.getExpiredAt().getTime() <= Timestamp.from(Instant.now()).getTime()) {
		//				throw new InvalidOtpException("OTP expired, please generate a new one");
		//			}
		//			
		//			else if(storedOtp == otp) {
		//				otpObject.setExpiredAt(Timestamp.from(Instant.now()));
		//				mfaValidationRepsitory.save(otpObject);
		//				OtpResponseDTO otpResponseDTO = new OtpResponseDTO("otp validated, download will start soon");
		//				
		//				return otpResponseDTO;
		//			}
		//		});
		OtpResponseDTO otpResponseDTO = new OtpResponseDTO();
		for (MfaValidation otpObject : listMfaValidationList) {

			if (otpObject.getOtp() == otp) {
				if (otpObject.getExpiredAt().getTime() <= Timestamp.from(Instant.now()).getTime()) {
					throw new InvalidOtpException("OTP expired, please generate a new one");
				} else {
					otpObject.setValidatedAt(Timestamp.from(Instant.now()));
					mfaValidationRepsitory.save(otpObject);
					otpResponseDTO.setMessage("otp validated, download will start soon");
					flag = true;

				}
			}
		}

		if (flag == false) {
			throw new InvalidOtpException("Wrong OTP entered");
		}

		return otpResponseDTO;

	}
}
