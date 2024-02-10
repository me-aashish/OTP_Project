package com.otp.project.OTPProject.entities;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mfa_validation")
@Entity
public class MfaValidation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "otp")
	private int otp;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "expiry_at")
	private Timestamp expiryAt;

	@Column(name = "validated_at")
	private Timestamp validatedAt;

	@PrePersist
	protected void onCreate() {
		Timestamp currentTimestamp = Timestamp.from(Instant.now());
		this.createdAt = currentTimestamp;
		this.expiryAt = new Timestamp(currentTimestamp.getTime() + (5 * 60 * 1000)); // Add 5 minutes in milliseconds
	}

}
