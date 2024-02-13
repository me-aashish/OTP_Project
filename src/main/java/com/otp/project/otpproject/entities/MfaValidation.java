package com.otp.project.otpproject.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Table(name = "mfa_validation")
@Entity
@NoArgsConstructor
public class MfaValidation {

	@Id
	@Column(name = "uuid")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid;

	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "otp")
	private String otp;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "expiry_at")
	private Timestamp expiryAt;

	@Column(name = "validated_at")
	private Timestamp validatedAt;

	@Column(name = "is_active")
	private boolean isActive;

	public void onCreate(int getOtpExpirationTimeInMinutes) {
		Timestamp currentTimestamp = Timestamp.from(Instant.now());
		this.createdAt = currentTimestamp;
		this.expiryAt = new Timestamp(currentTimestamp.getTime() + (getOtpExpirationTimeInMinutes * 60 * 1000)); // Add otp expiration time in milliseconds
	}

}
