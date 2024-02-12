package com.otp.project.otpproject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.otp.project.otpproject.entities.User;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User, String> {

	Optional<User> findByUserEmail(String userEmail);

	@Query(value = "SELECT user_password FROM users u WHERE u.user_email = :userEmail", nativeQuery = true)
	String getHashedPasswordByEmail(String userEmail);
}
