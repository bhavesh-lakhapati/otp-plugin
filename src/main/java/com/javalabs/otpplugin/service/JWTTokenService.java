package com.javalabs.otpplugin.service;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Claims;

@Service
public class JWTTokenService {
	@Value("${security.jwt.secret:r00t}")
	private String secretKey;
	
	@Value("${security.jwt.expiry:300000}")
	private long expiryInMilliseconds;
	
	private Date date = new Date();
	
	@PostConstruct
	private void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String createToken(final String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(date)
				.setExpiration(new Date(date.getTime() * expiryInMilliseconds))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public String getEmailFromToken(final String token) {
		if(validateToken(token)) {
			Claims claims = (Claims)Jwts.parser().setSigningKey(secretKey).parse(token).getBody();
			String email = claims.getSubject();
			return email;
		}
		
		return null;
	}
	
	public boolean validateToken(final String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parse(token);
			
			return true;
		} catch(final JwtException | IllegalArgumentException exception) {
			exception.printStackTrace();
			
			return false;
		}
	}
}
