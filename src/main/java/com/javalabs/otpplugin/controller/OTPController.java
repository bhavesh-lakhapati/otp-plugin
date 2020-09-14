package com.javalabs.otpplugin.controller;

import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.javalabs.otpplugin.service.OTPService;

@RestController
@CrossOrigin(exposedHeaders = {"X-Auth-Token"})
public class OTPController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OTPController.class);
	
	@Autowired
	private OTPService otpService;
	
	@PostMapping("/generate")
	public ResponseEntity<Map<String, Object>> generateOTP(@RequestBody final Map<String, String> requestBody) {
		String email = requestBody.get("email");
		HttpHeaders headers = new HttpHeaders();
		Map<String, Object> body = new HashMap<>();
		
		if(email != null && !email.isBlank()) {
			headers.set("X-Auth-Token", otpService.generateOtp(email));
			ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(body, headers, HttpStatus.OK);
			
			responseEntity.getHeaders()
				.forEach((key, value) -> {
					LOGGER.info(String.format("====> %s : %s <====", key, value));
				});
			return responseEntity;
		} else {
			body.put("error", "Email Address is required");
			return ResponseEntity.badRequest().body(body);
		}
	}
	
	@PostMapping("/verify")
	public ResponseEntity<Map<String, Object>> verifyOTP(@RequestHeader("X-Auth-Token") final String token, @RequestBody Map<String, Object> requestBody) {
		Integer otp = (Integer)requestBody.get("otp");
		String result = otpService.validateOTP(token, otp);
		
		Map<String, Object> body = new HashMap<>();
		if(result == null) {
			return ResponseEntity.ok(body);
		} else {
			body.put("error", result);
			return ResponseEntity.badRequest().body(body);
		}
	}
}
