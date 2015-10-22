/**
 * 
 */
package com.sap.hackthon.framework.beans;

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

	@Override
	public void setProperty(String property, Object value) {
		try {
			super.setProperty(property, value);
		} catch (NoSuchMethodException e) {
			setUserDefinedField(property, value);
		}
	}

	@Override
	public Object getProperty(String property){
		try {
			return super.getProperty(property);
		} catch (NoSuchMethodException e) {
			return getUserDefinedField(property);
		}
	}
	
}
