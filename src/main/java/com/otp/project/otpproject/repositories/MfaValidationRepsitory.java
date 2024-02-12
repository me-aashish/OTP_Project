package com.otp.project.otpproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.otp.project.otpproject.entities.MfaValidation;

import jakarta.transaction.Transactional;

@Repository
public interface MfaValidationRepsitory extends JpaRepository<MfaValidation, Integer> {

	MfaValidation getByUserEmail(String email);

	@Query(value = "SELECT * FROM mfa_validation m WHERE m.user_email = :email AND m.is_active = true", nativeQuery = true)
	MfaValidation getActiveOtp(String email);

	@Transactional
	@Modifying
	@Query(value = "UPDATE mfa_validation SET is_active = false WHERE user_email = :email", nativeQuery = true)
	void markPreviousOtpInactive(String email);

}
