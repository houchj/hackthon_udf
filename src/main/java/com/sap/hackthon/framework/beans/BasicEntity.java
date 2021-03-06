package com.sap.hackthon.framework.beans;

import java.util.Map;

public interface BasicEntity {
	
	public String getTenantId();

	public void setTenantId(String tenantId);
	
	public String getObjectType();

	public void setObjectType(String objectType);
	
	public void setProperty(String property, Object value) throws NoSuchMethodException;
	
	public Object getProperty(String property) throws NoSuchMethodException;
	
	public Map<String, Object> getProperties();
}
