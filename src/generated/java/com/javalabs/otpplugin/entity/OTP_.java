package com.javalabs.otpplugin.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OTP.class)
public abstract class OTP_ {

	public static volatile SingularAttribute<OTP, String> otp;
	public static volatile SingularAttribute<OTP, Long> id;
	public static volatile SingularAttribute<OTP, String> email;

	public static final String OTP = "otp";
	public static final String ID = "id";
	public static final String EMAIL = "email";

}

