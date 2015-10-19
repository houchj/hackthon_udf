package com.sap.hackthon.entity;

import javax.persistence.MappedSuperclass;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

@MappedSuperclass
@Multitenant
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "multi-tenant.id")
public interface BasicEntity {
	
	public String getTenantId();

	public void setTenantId(String tenantId);
	
	public String getObjectType();

	public void setObjectType(String objectType);
}
