package com.otp.project.otpproject.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

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

	@PrePersist
	protected void onCreate() {
		Timestamp currentTimestamp = Timestamp.from(Instant.now());
		this.createdAt = currentTimestamp;
		this.expiryAt = new Timestamp(currentTimestamp.getTime() + (5 * 60 * 1000)); // Add 5 minutes in milliseconds
	}

}
