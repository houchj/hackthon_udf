package com.sap.hackthon.entity;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MappedSuperclass
@Multitenant
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "multi-tenant.id")
public abstract class BasicEntityAdapter implements BasicEntity{

	private static final Logger LOGGER = LoggerFactory.getLogger(BasicEntity.class);
	
	@Column(name = "OBJECT_TYPE")
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

	public void setProperty(String property, Object value) throws NoSuchMethodException{
		try {
			PropertyUtils.setProperty(this, property, value);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOGGER.warn("Unreachable property: {}", property);
		} catch (NoSuchMethodException e) {
			throw e;
		}
	}

	public Object getProperty(String property) throws NoSuchMethodException{
		Object ret = null;
		try {
			ret = PropertyUtils.getProperty(this, property);
		} catch (IllegalAccessException | InvocationTargetException ex) {
			LOGGER.warn("Unreachable property: {}", property);
		} catch (NoSuchMethodException e){
			throw e;
		} 
		return ret;
	}
}
