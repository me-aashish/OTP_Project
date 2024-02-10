package com.otp.project.OTPProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.otp.project.OTPProject.entities.MfaValidation;

@Repository
public interface MfaValidationRepsitory extends JpaRepository<MfaValidation, Integer> {

	MfaValidation getByUserEmail(String email);

	@Query(value = "SELECT * FROM mfa_validation m WHERE m.user_email = :email", nativeQuery = true)
	List<MfaValidation> getOtpList(String email);

}
