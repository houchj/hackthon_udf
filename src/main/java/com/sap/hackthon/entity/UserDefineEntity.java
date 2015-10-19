/**
 * 
 */
package com.sap.hackthon.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author I310717
 *
 */
@MappedSuperclass
public abstract class UserDefineEntity implements BasicEntity{

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
