package com.sap.hackthon.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

@MappedSuperclass
@Multitenant
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "multi-tenant.id")
public abstract class BasicEntityAdapter implements BasicEntity{

	@Column(name = "OBJECT_TYPE", insertable = false, updatable = false)
	protected String objectType; 
	
	@Column(name = "TENANT_ID", insertable = false, updatable = false)
	protected String tenantId;
	
	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
