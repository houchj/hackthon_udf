/**
 * 
 */
package com.sap.hackthon.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author I310717
 *
 */
@MappedSuperclass
public abstract class UserDefineEntity extends BasicEntityAdapter{

	@Transient
	private Map<String, Object> userDefineFields = new HashMap<String, Object>();

	

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
