package com.otp.project.OTPProject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.otp.project.OTPProject.entities.User;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User, String> {

	Optional<User> findByUserEmail(String userEmail);
}
