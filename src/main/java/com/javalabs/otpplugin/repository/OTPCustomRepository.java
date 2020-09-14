package com.javalabs.otpplugin.repository;

import com.javalabs.otpplugin.entity.OTP;

public interface OTPCustomRepository {
	OTP findByEmailAndCode(final String email, final int otp);
}
