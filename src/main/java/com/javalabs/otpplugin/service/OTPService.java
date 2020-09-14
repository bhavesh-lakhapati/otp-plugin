package com.javalabs.otpplugin.service;

import org.springframework.stereotype.Service;

import com.javalabs.otpplugin.repository.OTPRepository;
import com.javalabs.otpplugin.entity.OTP;

@Service
public class OTPService {
	private final JWTTokenService jwtTokenService;
	private final OTPRepository otpRepository;
	
	public OTPService(final JWTTokenService jwtTokenService, final OTPRepository otpRepository) {
		this.jwtTokenService = jwtTokenService;
		this.otpRepository = otpRepository;
	}
	
	public String generateOtp(final String email) {
		int code = (int)(Math.random() * 10000);
		
		OTP otp = new OTP();
		otp.setEmail(email);
		otp.setCode(code);
		
		otpRepository.save(otp);
		
		return jwtTokenService.createToken(email);
	}
	
	public String validateOTP(final String token, final int code) {
		String email = jwtTokenService.getEmailFromToken(token);
		if(email != null) {
			OTP otp = otpRepository.findByEmailAndCode(email, code);
			if(otp != null) {
				otpRepository.delete(otp);
				
				return null;
			}
			
			return "OTP doesnot exists/expired for given email address. Please generate new OTP.";
		}
		
		return "Session expired.";
	}
}
