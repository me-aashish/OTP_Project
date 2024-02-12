package com.otp.project.otpproject.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.otp.project.otpproject.entities.MfaValidation;

import jakarta.transaction.Transactional;

@Repository
public interface MfaValidationRepsitory extends JpaRepository<MfaValidation, UUID> {

	MfaValidation getByUserEmail(String email);

	@Query(value = "SELECT * FROM mfa_validation m WHERE m.uuid = :requestId", nativeQuery = true)
	MfaValidation getActiveOtp(UUID requestId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE mfa_validation SET is_active = false WHERE user_email = :email", nativeQuery = true)
	void markPreviousOtpInactive(String email);

}
