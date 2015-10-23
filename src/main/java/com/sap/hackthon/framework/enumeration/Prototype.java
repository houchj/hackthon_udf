package com.sap.hackthon.framework.enumeration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;

public enum Prototype {

	STRING(String.class),
	BOOLEAN(Boolean.class),
	SHORT(Short.class),
	INTEGER(Integer.class),
	LONG(Long.class),
	BIGINT(BigInteger.class),
	FLOAT(Float.class),
	DOUBLE(Double.class),
	CHARACTER(Character.class),
	DECIMAL(BigDecimal.class),
	TIMESTAMP(Timestamp.class),
	DATETIME(DateTime.class),
	DATE(Date.class);
	
	private Class<?> innerClass;
	
	private Prototype(Class<?> innerClass){
		this.innerClass = innerClass;
	}
	
	public Class<?> getInnerClass(){
		return innerClass;
	}
}
