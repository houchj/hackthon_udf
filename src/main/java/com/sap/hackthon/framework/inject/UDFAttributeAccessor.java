package com.sap.hackthon.framework.inject;

import org.eclipse.persistence.exceptions.DescriptorException;
import org.eclipse.persistence.mappings.AttributeAccessor;

import com.sap.hackthon.framework.beans.UserDefineEntity;
import com.sap.hackthon.framework.exception.NoSuchUserDefineFieldException;

public class UDFAttributeAccessor extends AttributeAccessor {
	
	private static final long serialVersionUID = -7164663172293798085L;

	@Override
	public Object getAttributeValueFromObject(Object entity) throws DescriptorException {
		if(entity == null){
			throw new IllegalArgumentException("Instance of accessor can't be null");
		}
		if(!UserDefineEntity.class.isAssignableFrom(entity.getClass())){
			throw new IllegalArgumentException("Instance must be inherit from " + UserDefineEntity.class.getName());
		}
		UserDefineEntity ude = (UserDefineEntity) entity;
		return ude.getUserDefinedField(getAttributeName());
	}

	@Override
	public void setAttributeValueInObject(Object entity, Object value) throws DescriptorException {
		if(entity == null){
			throw new IllegalArgumentException("Instance of accessor can't be null");
		}
		if(!UserDefineEntity.class.isAssignableFrom(entity.getClass())){
			throw new IllegalArgumentException("Instance must be inherit from " + UserDefineEntity.class.getName());
		}
		String attribute = getAttributeName();
		if(attribute == null){
			throw new IllegalArgumentException("Attribute name can't be null");
		}
		UserDefineEntity ude = (UserDefineEntity) entity;
		try {
			ude.setUserDefinedField(getAttributeName(), value);
		} catch (NoSuchUserDefineFieldException e) {
			/*Never reach here*/
		}
	}

}
