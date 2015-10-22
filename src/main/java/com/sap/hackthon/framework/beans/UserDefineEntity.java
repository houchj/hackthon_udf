/**
 * 
 */
package com.sap.hackthon.framework.beans;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.framework.exception.NoSuchUserDefineFieldException;
import com.sap.hackthon.framework.mata.MetaInfoRetriever;
import com.sap.hackthon.framework.utils.UDFTypeConvertor;

/**
 * @author I310717
 *
 */
@MappedSuperclass
public abstract class UserDefineEntity extends BasicEntityAdapter{
	
	@Autowired
	private MetaInfoRetriever metaInfo;

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
	
	public void setUserDefinedField(String name, Object value) throws NoSuchUserDefineFieldException{
		PropertyMeta pMeta = metaInfo.retrievePropertyMeta(name);
		if(pMeta == null){
			throw new NoSuchUserDefineFieldException(name);
		}
		userDefineFields.put(name, UDFTypeConvertor.convert(pMeta, value));
	}

	@Override
	public void setProperty(String property, Object value) throws NoSuchUserDefineFieldException {
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
	
	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> pros = super.getProperties();
		pros.putAll(userDefineFields);
		return pros;
	}
	
}
