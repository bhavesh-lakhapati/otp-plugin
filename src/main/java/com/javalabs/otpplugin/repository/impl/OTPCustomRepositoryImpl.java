package com.javalabs.otpplugin.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.javalabs.otpplugin.entity.OTP;
import com.javalabs.otpplugin.entity.OTP_;
import com.javalabs.otpplugin.repository.OTPCustomRepository;

@Repository
public class OTPCustomRepositoryImpl implements OTPCustomRepository {
	@PersistenceContext
	private final EntityManager em;
	private final CriteriaBuilder cb;
	
	public OTPCustomRepositoryImpl(final EntityManager em) {
		this.em = em;
		this.cb = em.getCriteriaBuilder();
	}
	
	public OTP findByEmailAndCode(final String email, final int otp) {
		CriteriaQuery<OTP> cq = em.getCriteriaBuilder().createQuery(OTP.class);
		Root<OTP> root = cq.from(OTP.class);
		
		cq.select(root);
		cq.where(cb.and(
			cb.equal(root.get(OTP_.code), otp),
			cb.equal(root.get(OTP_.email), email)
		));
		
		try {
			return em.createQuery(cq).getSingleResult();
		} catch(final NoResultException exception) {
			return null;
		}
	}
}
