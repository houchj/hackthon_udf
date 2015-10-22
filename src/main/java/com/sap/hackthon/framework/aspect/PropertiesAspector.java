package com.sap.hackthon.framework.aspect;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.hackthon.framework.beans.GlobalSettings;
import com.sap.hackthon.framework.inject.UDFInjector;
import com.sap.hackthon.framework.utils.GlobalConstants;

@Aspect
@Component	
public class PropertiesAspector {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private GlobalSettings settings;
	
	@Autowired
	protected UDFInjector injector;


	@Before("execution(public * com.sap.hackthon.services..*.*(..))")
	public void tenantPointCuts1(){
		injectTenant();
	}
	
	@Before("execution(public * com.sap.hackthon.framework.meta.MetaInfoRetriever.*(..))")
	public void tenantPointCuts2(){
		injectTenant();
	}
	
	@Before("execution(public * com.sap.hackthon.services.biz..*Service.*(..))")
	public void injectUDFMapping(){
		injector.injectUDFMappings();
	}
	
	private void injectTenant(){
		Object tenantId = settings.getVariable(GlobalConstants.TENANT);
		if(tenantId == null){
			return;
		}
		entityManager.setProperty("multi-tenant.id", tenantId);
	}
}
