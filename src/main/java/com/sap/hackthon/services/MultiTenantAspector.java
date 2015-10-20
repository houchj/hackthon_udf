package com.sap.hackthon.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.sap.hackthon.entity.GlobalSettings;
import com.sap.hackthon.utils.GlobalConstants;

@Aspect
public class MultiTenantAspector {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private GlobalSettings settings;
	
	@Autowired
	private PropertyMetaService metaService;


	@Before("execution(public * com.sap.hackthon.services..*.*(..))")
	public void injectTenant(){
		Object tenantId = settings.getVariable(GlobalConstants.TENANT);
		if(tenantId == null){
			return;
		}
		entityManager.setProperty("multi-tenant.id", tenantId);
	}
	
	@Before("execution(public * com.sap.hackthon.services.EntityService.*(..))")
	public void injectUDFMapping(){
		metaService.scanAndInstallProperties();
	}

}