package com.example.demo.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private String signatureKey = "";
	
	public JWTService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey secKey = keyGen.generateKey();
			signatureKey = Base64.getEncoder().encodeToString(secKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return Jwts.builder().claims().add(claims).subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.and()
				.signWith(getSignatureKey()).compact();
	}

	private SecretKey getSignatureKey() {
		byte[] keyBytes = Decoders.BASE64.decode(signatureKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSignatureKey()).build().parseSignedClaims(token).getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
