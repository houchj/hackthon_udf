/**
 * 
 */
package com.sap.hackthon.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * @author I310717
 *
 */
@MappedSuperclass
@Multitenant
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "multi-tenant.id")
public abstract class UserDefineEntity {

	@Column(name = "OBJECT_TYPE", insertable = false, updatable = false)
	protected String objectType; 
	
	@Column(name = "TENANT_ID", insertable = false, updatable = false)
	protected String tenantId;
	
	@Transient
	private Map<String, Object> userDefineFields = new HashMap<String, Object>();

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

	public Map<String, Object> getUserDefineFields() {
		return userDefineFields;
	}

	public void setUserDefineFields(Map<String, Object> userDefineFields) {
		this.userDefineFields = userDefineFields;
	}
	
	public Object getUserDefinedField(String name){
		return userDefineFields.get(name);
	}
	
	public void setUserDefinedField(String name, Object value){
		userDefineFields.put(name, value);
	}
}
