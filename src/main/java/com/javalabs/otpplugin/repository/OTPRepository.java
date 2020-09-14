package com.javalabs.otpplugin.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javalabs.otpplugin.entity.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long>, OTPCustomRepository {

}
