package com.otp.project.OTPProject.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${encryption.key}")
	private String encryptionKey;

	public String extractUserEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsresover) {
		final Claims claims = extractAllClaims(token);
		return claimsresover.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(encryptionKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// token generation using extra claims
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	// token generation without extra claims and only subject
	public String generateToken(UserDetails userDetails) {

		return generateToken(new HashMap<>(), userDetails);
	}

	// token validation
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userEmail = extractUserEmail(token);

		return (userEmail.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	// checking token is expired or not
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// extracting expiration date
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
